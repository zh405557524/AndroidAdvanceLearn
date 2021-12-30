package com.soul.oldskin;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

/**
 * Description: 注册Activity生命周期监听
 * Author: 祝明
 * CreateDate: 2021/10/15 11:53
 * UpdateUser:
 * UpdateDate: 2021/10/15 11:53
 * UpdateRemark:
 */
public class SkinActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = SkinActivityLifecycleCallbacks.class.getSimpleName();
    private SkinFactory skinFactory;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");

        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        // 利用反射去修改mFactorySet的值为false，防止抛出 A factory has already been set on this...
        // 反正就是为了提高健壮性
        try {
            // 尽量使用LayoutInflater.class，不要使用layoutInflater.getClass()
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            // 源码312行
            mFactorySet.setAccessible(true);
            mFactorySet.set(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "reflect failed e: " + e.toString());
        }

        skinFactory = new SkinFactory(activity);
        // mFactorySet = true是无法设置成功的（源码312行）
        LayoutInflaterCompat.setFactory2(layoutInflater, skinFactory);

        // 注册观察者（监听用户操作，点击了换肤，通知观察者更新）
        SkinEngine.getInstance().addObserver(skinFactory);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.i(TAG, "onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.i(TAG, "onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.i(TAG, "onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.i(TAG, "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Log.i(TAG, "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.i(TAG, "onActivityDestroyed");
    }
}
