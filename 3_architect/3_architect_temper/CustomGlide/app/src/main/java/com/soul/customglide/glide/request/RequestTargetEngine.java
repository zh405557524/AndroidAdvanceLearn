package com.soul.customglide.glide.request;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.soul.customglide.glide.cache.ActiveCache;
import com.soul.customglide.glide.cache.MemoryCache;
import com.soul.customglide.glide.cache.MemoryCacheCallback;
import com.soul.customglide.glide.cache.disk.DiskLruCacheImpl;
import com.soul.customglide.glide.fragment.LifecycleCallback;
import com.soul.customglide.glide.load_data.LoadDataManger;
import com.soul.customglide.glide.load_data.ResponseListener;
import com.soul.customglide.glide.pool.BitmapPool;
import com.soul.customglide.glide.pool.LruBitmapPool;
import com.soul.customglide.glide.resource.Key;
import com.soul.customglide.glide.resource.Value;
import com.soul.customglide.glide.resource.ValueCallback;
import com.soul.customglide.utils.Tool;

/**
 * @Description：加载图片资源
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 15:10
 */
public class RequestTargetEngine implements LifecycleCallback, ValueCallback, MemoryCacheCallback, ResponseListener {

    private static final String TAG = RequestTargetEngine.class.getSimpleName();

    /**
     * 内存缓存的最大值
     * 图片复用池
     */
    private static final int MEMORY_MAX_SIZE = 1024 * 1024 * 60;

    private Context glideContext;

    /**
     * 图片地址
     */
    private String path;
    /**
     * 图片地址对应的key值
     */
    private String mKey;
    /**
     * 图片容器
     */
    private ImageView mImageView;
    /**
     * 活动缓存
     */
    private ActiveCache mActiveCache;

    /**
     * 内存缓存
     */
    private MemoryCache mMemoryCache;

    /**
     * 磁盘缓存
     */
    private DiskLruCacheImpl mDiskLruCache;

    private BitmapPool mBitmapPool;


    public RequestTargetEngine() {
        if (mActiveCache == null) {
            mActiveCache = new ActiveCache(this);
        }
        if (mMemoryCache == null) {
            mMemoryCache = new MemoryCache(MEMORY_MAX_SIZE);
            mMemoryCache.setMemoryCacheCallback(this);
        }
        if (mBitmapPool == null) {
            mBitmapPool = new LruBitmapPool(MEMORY_MAX_SIZE);
        }
        mDiskLruCache = new DiskLruCacheImpl();
    }

    @Override
    public void glideInitAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经开启了 初始化了....");
    }

    @Override
    public void glideStopAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经停止中 ....");
    }

    @Override
    public void glideRecycleAction() {

        Log.d(TAG, "glideInitAction: Glide生命周期之 进行释放操作 缓存策略释放操作等 >>>>>> ....");

        if (mActiveCache != null) {
            mActiveCache.closeThread(); // 把活动缓存给释放掉
        }
        //把内存缓存移除

    }

    public void loadValueInitAction(String path, Context requestManagerContext) {
        this.path = path;
        this.glideContext = requestManagerContext;
        mKey = new Key(path).getKey();

    }

    public void into(ImageView imageView) {
        mImageView = imageView;
        Tool.checkNotEmpty(imageView);
        Tool.assertMainThread();

        //加载资源
        Value value = cacheAction();
        if (value != null) {
            //使用完成了 减一
            value.nonUseAction();
            imageView.setImageBitmap(value.getBitmap());
        }
    }


    /**
     * 活动缓存间接的调用Value所发出的
     * 回调告诉外界，Value资源不再使用了
     * 监听的方法（Value不再使用了）
     *
     * @param key
     * @param value
     */
    @Override
    public void valueNonUseListener(String key, Value value) {
        //把活动缓存操作的value资源加入到内存缓存中
        if (key != null && value != null) {
            mMemoryCache.put(key, value);
        }
    }


    /**
     * 内存缓存不使用的回调
     *
     * @param key
     * @param oldValue
     */
    @Override
    public void entryRemoveMemoryCache(String key, Value oldValue) {
        //添加到复用池
        mBitmapPool.put(oldValue.getBitmap());
    }

    /**
     * 加载外部资源成功
     *
     * @param value
     */
    @Override
    public void responseSuccess(Value value) {
        //加载资源成功
        if (value != null) {
            saveCache(mKey, value);
            mImageView.setImageBitmap(value.getBitmap());
        }
    }

    /**
     * 加载资源失败
     *
     * @param e
     */
    @Override
    public void responseException(Exception e) {
        Log.d(TAG, "responseException:加载外部资源失败 e:" + e.getMessage());
    }

    /**
     * 从内存中获取数据
     *
     * @return
     */
    private Value cacheAction() {
        //1、从活动缓存中获取
        //2、从内存缓存中获取
        //3、从磁盘获取
        //4、从网络获取

        Value value = mActiveCache.get(mKey);

        if (value != null) {
            Log.d(TAG, "cacheAction:本次加载时在(活动缓存)中获取的资源");
            value.useAction();//使用了一次 加一
            return value;
        }

        value = mMemoryCache.get(mKey);
        if (value != null) {
            mMemoryCache.shoudnRemove(mKey);//移除内存缓存
            mActiveCache.put(mKey, value);//把内存缓存中的元素，加入到活动缓存中
            Log.d(TAG, "cacheAction:本次加载时在(内存缓存)中获取的资源");
            value.useAction();
            return value;
        }


        value = mDiskLruCache.get(mKey, mBitmapPool);
        if (value != null) {
            mActiveCache.put(mKey, value);
            Log.d(TAG, "cacheAction: 本次加载时在(磁盘缓存)中获取的资源");
            value.useAction();
            return value;
        }

        value = new LoadDataManger().loadResource(path, this, glideContext);

        if (value != null) {
            return value;
        }
        return null;
    }


    /**
     * 保存资源到缓存中
     *
     * @param key
     * @param value
     */
    private void saveCache(String key, Value value) {
        Log.d(TAG, "saveCache:--加载外部资源成功后，保存到缓存中 key:" + mKey);
        value.setKey(key);
        if (mDiskLruCache != null) {
            //保存到磁盘缓存中
            mDiskLruCache.put(key, value);
        }
    }

}

































