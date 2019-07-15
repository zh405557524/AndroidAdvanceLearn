package com.soul.gifplay;

import android.graphics.Bitmap;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-08 14:19
 * UpdateUser:
 * UpdateDate: 2019-07-08 14:19
 * UpdateRemark:
 */
public class GifHandler {

    private long gifAdder;

    static {
        System.loadLibrary("native-lib");
    }

    public GifHandler(String path) {
        this.gifAdder = loadPath(path);
    }


    public int getWidth() {
        return getWidth(gifAdder);
    }

    public int getHeight() {
        return getHeight(gifAdder);
    }

    public int updateFrame(Bitmap bitmap) {
        return updateFrame(gifAdder, bitmap);
    }

    private native long loadPath(String path);

    public native int getWidth(long ndkGif);

    public native int getHeight(long ndkGif);

    //隔一段时间，调用一次
    public native int updateFrame(long ndkGif, Bitmap bitmap);
}
