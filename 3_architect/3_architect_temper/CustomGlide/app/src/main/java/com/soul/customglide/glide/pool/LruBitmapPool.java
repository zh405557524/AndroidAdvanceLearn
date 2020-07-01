package com.soul.customglide.glide.pool;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;

import java.util.TreeMap;

/**
 * @Description：bitmap复用池
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/6 13:23
 */
public class LruBitmapPool extends LruCache<Integer, Bitmap> implements BitmapPool {

    private final String TAG = LruBitmapPool.class.getSimpleName();

    private TreeMap<Integer, String> treeMap = new TreeMap<>();

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruBitmapPool(int maxSize) {
        super(maxSize);
    }


    @Override
    public void put(Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            if (bitmap.isRecycled() == false) {
                bitmap.recycle();
            }
            Log.d(TAG, "put: 复用的条件1 Bitmap.ismutable 是false，条件不满足，不能复用 添加..." + bitmap);
        }

        int bitmapSize = getBitmapSize(bitmap);
        if (bitmapSize > maxSize()) {
            if (bitmap.isRecycled() == false) {
                bitmap.recycle();
                Log.d(TAG, "put: 复用的条件2 Bitmap.Size大于LruMaxSize，条件不满足，不能复用 添加...");
            }
        }

        //添加到 LRU Cache中
        put(bitmapSize, bitmap);

        //保存到treeMap是为了
        treeMap.put(bitmapSize, null);
    }

    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {
        int getSize = width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
        Integer key = treeMap.ceilingKey(getSize);// 获得 getSize这么大的key，同时还可以获得 比 getSize还要大的key
        if (key == null) {
            return null;//如果找不到保存的key，就直接返回null，无法复用
        }
        if (key <= (getSize * 2)) {
            Bitmap remove = remove(key);
            Log.d(TAG, "get: 从复用池获取:" + remove);
            return remove;
        }

        return null;
    }

    @Override
    protected int sizeOf(Integer key, Bitmap value) {
        return getBitmapSize(value);
    }

    /**
     * 获取bitmap的大小
     *
     * @param bitmap
     * @return
     */
    private int getBitmapSize(Bitmap bitmap) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else {
            return bitmap.getByteCount();
        }
    }

}























