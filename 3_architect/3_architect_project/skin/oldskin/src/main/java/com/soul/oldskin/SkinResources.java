package com.soul.oldskin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;

/**
 * Description: 访问 本地存储的皮肤包资源 或者当前app运行的apk 包资源
 * Author: 祝明
 * CreateDate: 2021/11/29 11:00
 * UpdateUser:
 * UpdateDate: 2021/11/29 11:00
 * UpdateRemark:
 */
public class SkinResources {


    private final Resources mAppResources;

    private static SkinResources instance;

    private boolean isDefaultSkin = true; // 默认是 默认的皮肤(最原始的)

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public static void applicationInit(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    /**
     * 获取字体
     *
     * @param resId
     * @return
     */
    public Typeface getTypeface(int resId) {
        getString(resId);

        return null;
    }

    public Object getBackground(int attrValueInt) {

        return null;
    }

    private String getString(int resId) {
        try {
            if (isDefaultSkin) {//如果没有皮肤，那就加载当前app运行的apk资源
                return mAppResources.getString(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

}
