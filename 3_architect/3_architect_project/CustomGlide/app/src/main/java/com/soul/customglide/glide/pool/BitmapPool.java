package com.soul.customglide.glide.pool;

import android.graphics.Bitmap;

/**
 * @Description：bitmap池 接口
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/6 13:22
 */
public interface BitmapPool {

    /**
     * 加入到bitmap内存复用池
     *
     * @param bitmap
     */
    void put(Bitmap bitmap);


    /**
     * 从bitmap内存复用池里面取出来
     *
     * @param width
     * @param height
     * @param config
     * @return
     */
    Bitmap get(int width, int height, Bitmap.Config config);

}
