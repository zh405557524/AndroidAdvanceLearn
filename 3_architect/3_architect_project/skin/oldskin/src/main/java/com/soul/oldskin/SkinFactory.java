package com.soul.oldskin;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: xml 解析工厂
 * Author: 祝明
 * CreateDate: 2021/10/15 11:54
 * UpdateUser:
 * UpdateDate: 2021/10/15 11:54
 * UpdateRemark:
 */
public class SkinFactory implements LayoutInflater.Factory2, Observer {

    private Activity activity;
    /**
     * android 包名前缀
     */
    private static final String[] sClassPrefixList = {"android.widget", "android.webkit", "android.app"};


    public SkinFactory(Activity activity) {
        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}





