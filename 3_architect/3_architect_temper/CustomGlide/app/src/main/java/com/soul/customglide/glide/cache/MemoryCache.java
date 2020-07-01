package com.soul.customglide.glide.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.soul.customglide.glide.resource.Value;

/**
 * @Description：内存缓存---lru算法
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 2:31
 */
public class MemoryCache extends LruCache<String, Value> {


    private MemoryCacheCallback mMemoryCacheCallback;
    private boolean mIsShoudonRemove;

    public MemoryCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Value value) {

        Bitmap bitmap = value.getBitmap();


        // 最开始的时候
        // int result = bitmap.getRowBytes() * bitmap.getHeight();

        // API 12  3.0
        // result = bitmap.getByteCount(); // 在bitmap内存复用上有区别 （所属的）

        // API 19 4.4
        // result = bitmap.getAllocationByteCount(); // 在bitmap内存复用上有区别 （整个的）

        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }

    /**
     * 1 重复的key
     * 2 最少使用的元素会被移除
     *
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        if (mMemoryCacheCallback != null && !mIsShoudonRemove) {
            mMemoryCacheCallback.entryRemoveMemoryCache(key, oldValue);

        }
    }

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        mMemoryCacheCallback = memoryCacheCallback;
    }

    /**
     * 手动移除
     *
     * @param key
     */
    public Value shoudnRemove(String key) {
        mIsShoudonRemove = true;
        Value value = remove(key);
        mIsShoudonRemove = false;
        return value;
    }


}
