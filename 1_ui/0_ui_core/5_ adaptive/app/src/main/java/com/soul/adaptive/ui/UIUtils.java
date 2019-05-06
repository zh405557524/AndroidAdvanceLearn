package com.soul.adaptive.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/5/5 上午10:33
 * UpdateUser:
 * UpdateDate: 2019/5/5 上午10:33
 * UpdateRemark:
 */
public class UIUtils {

    private static UIUtils instance;

    //标准尺寸
    public static final float standard_width = 1080f;
    public static final float standard_height = 1920f;

    //实际设备信息
    public static float displayMetricsWidth;
    public static float displayMetricsHeight;
    private int mSystemBarHeight;

    public static UIUtils getInstance(Context context) {
        if (instance == null) {
            instance = new UIUtils(context);

        }
        return instance;
    }

    public static UIUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("UIUtil应该先调用含有构造方法进行初始化");
        }
        return instance;
    }

    private UIUtils(Context context) {
        //计算缩放系数

        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        if (displayMetricsWidth == 0.0f || displayMetricsHeight == 0.0f) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            //状态栏
            mSystemBarHeight = getSystemBarHeight(context);
            //判断横屏还是竖屏
            if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                //横屏
                displayMetricsWidth = (float) displayMetrics.widthPixels;
                displayMetricsHeight = (float) (displayMetrics.heightPixels - mSystemBarHeight);
            } else {
                //竖屏
                displayMetricsWidth = (float) displayMetrics.widthPixels;
                displayMetricsHeight = (float) (displayMetrics.heightPixels - mSystemBarHeight);
            }

        }
    }

    public float getHorizontalScaleValue() {
        return ((float) displayMetricsWidth) / standard_width;
    }

    public float getVerticalScaleValue() {
        return (displayMetricsHeight) / (standard_height - mSystemBarHeight);
    }


    private int getSystemBarHeight(Context context) {
        return getValue(context, "com.android.internal.R$dimen",
                "system_bar_height", 48);
    }


    private int getValue(Context context, String dimeClass, String system_bar_height, int defaultValue) {
        try {
            final Class<?> clz = Class.forName(dimeClass);
            final Object object = clz.newInstance();
            final Field field = clz.getField(system_bar_height);
            final int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelOffset(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


    public int getWidth(int width) {

        return Math.round((float) width * displayMetricsWidth / standard_width);
    }

    public int getHeight(int height) {
        return Math.round((float) height * displayMetricsHeight / (standard_height - mSystemBarHeight));
    }
}
