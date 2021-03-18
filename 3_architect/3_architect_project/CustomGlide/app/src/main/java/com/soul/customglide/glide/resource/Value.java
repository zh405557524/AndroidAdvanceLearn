package com.soul.customglide.glide.resource;

import android.graphics.Bitmap;
import android.util.Log;

import com.soul.customglide.utils.Tool;

/**
 * @描述：对bitmap的封装
 * @作者：祝明
 * @项目名:CustomGlide
 * @创建时间：2020/6/3 15:21
 */
public class Value {

    private final String TAG = Value.class.getSimpleName();


    // 单利模式
    private static Value value;


    private Bitmap mBitmap;

    /**
     * 使用计数
     */
    private int count;

    /**
     * 监听
     */
    private ValueCallback callback;

    /**
     * 定义key
     */
    private String key;

    public static Value getInstance() {
        if (null == value) {
            synchronized (Value.class) {
                if (null == value) {
                    value = new Value();
                }
            }
        }
        return value;
    }


    /**
     * 使用一次就加一
     */
    public void useAction() {
        Tool.checkNotEmpty(mBitmap);

        if (mBitmap.isRecycled()) {//已经被回收了
            Log.d(TAG, "useAction:已经被回收了");
            return;
        }
        Log.d(TAG, "useAction 加一 count:" + count);
        count++;
    }


    /**
     * 使用完成 (不是用) 就减一
     */
    public void nonUseAction() {
        if (count-- <= 0 && callback != null) {
            callback.valueNonUseListener(key, this);
        }
        Log.d(TAG, "useAction:减一 count:" + count);
    }

    /**
     * 释放
     */
    public void recycleBitmap() {
        if (count > 0) {
            Log.d(TAG, "recycleBitmap:引用计数大于0 ，证明还在使用中，不能去释放");
            return;
        }
        if (mBitmap.isRecycled()) {//被回收了
            Log.d(TAG, "recycleBitmap:mBitmap.isRecycled 已经被释放了");
            return;
        }
        mBitmap.recycle();
        value = null;
        System.gc();
    }


    public static Value getValue() {
        return value;
    }

    public static void setValue(Value value) {
        Value.value = value;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCallback(ValueCallback callback) {
        this.callback = callback;
    }
}
































