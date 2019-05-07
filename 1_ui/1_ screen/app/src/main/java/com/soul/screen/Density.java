package com.soul.screen;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Description:像素密度适配
 * Author: 祝明
 * CreateDate: 2019/5/7 上午11:32
 * UpdateUser:
 * UpdateDate: 2019/5/7 上午11:32
 * UpdateRemark:
 */
public class Density {


    private static float mAppDensity;
    private static float mAppScaledDensity;

    private static final float WIDTH = 480;//参考像素密度

    /**
     * 设置屏幕像素密度适配
     */
    protected static void setDensity(final Application application, Activity activity) {
        //获取当前屏幕像素，
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (mAppDensity == 0) {
            mAppDensity = displayMetrics.density;
            mAppScaledDensity = displayMetrics.scaledDensity;//字体缩放比例
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体缩放比例发生改变，修改字体比例
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mAppScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });

        }
        //根据标准的像素，计算出新的比例
        float targetDensity = displayMetrics.widthPixels / WIDTH;
        final float targetScaleDensity = targetDensity * (mAppScaledDensity / mAppDensity);
        int targetDensityDpi = (int) (targetDensity * 160);
        //重新给activity的DisplayMetrics设值
        final DisplayMetrics displayMetrics1 = activity.getResources().getDisplayMetrics();
        displayMetrics1.density = targetDensity;
        displayMetrics1.scaledDensity = targetScaleDensity;
        displayMetrics1.densityDpi = targetDensityDpi;
    }

}
