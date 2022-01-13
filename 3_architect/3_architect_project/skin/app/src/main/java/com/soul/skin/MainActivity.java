package com.soul.skin;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.soul.newskin.SkinManager;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 申请权限
        verifyStoragePermissions(this);
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
            SkinManager.getInstance().loaderSkinResources(skinPath);
        } catch (Exception e) {
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
        Log.e("netease >>> ", "-------------start-------------");
        long start = System.currentTimeMillis();

        try {
            SkinManager.getInstance().loaderSkinResources("分身乏术分身乏术发放松放松放松");
        } catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis() - start;
        Log.e("netease >>> ", "还原耗时（毫秒）：" + end);
        Log.e("netease >>> ", "-------------end---------------");
    }

    public void startActivityThis(View view) {

    }


    // 申请权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {

        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(activity,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}