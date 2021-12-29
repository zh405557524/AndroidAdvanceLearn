package com.soul.skin;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.soul.oldskin.SkinEngine;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 换肤
     *
     * @param view
     */
    public void skinAction(View view) {

        long l = System.currentTimeMillis();
        //获取换肤皮肤文件地址
        String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "my.skin";
        try {
            SkinEngine.getInstance().updateSkin(skinPath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Log.i("TAG", "换肤耗时：" + (System.currentTimeMillis() - l));
    }


    /**
     * 默认
     *
     * @param view
     */
    public void revertDefault(View view) {

    }

    public void startActivityThis(View view) {

    }
}