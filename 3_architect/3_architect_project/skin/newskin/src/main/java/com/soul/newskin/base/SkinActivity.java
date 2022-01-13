package com.soul.newskin.base;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.soul.newskin.core.CustomAppCompatViewInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

/**
 * Description: 换肤的activity
 * Author: 祝明
 * CreateDate: 2021/12/31 11:33
 * UpdateUser:
 * UpdateDate: 2021/12/31 11:33
 * UpdateRemark:
 */
public class SkinActivity extends AppCompatActivity {

    private CustomAppCompatViewInflater viewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (openChangeSkin()) {
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater(context);
            }
        }

        return super.onCreateView(name, context, attrs);
    }


    /**
     * 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     *
     * @return
     */
    protected boolean openChangeSkin() {
        return false;
    }

}
