package com.soul.customglide;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.soul.customglide.glide.Glide;
import com.soul.customglide.utils.PermissionsUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;


    String[] permissions = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_loadPic).setOnClickListener(this);
        mImageView = findViewById(R.id.iv_pic);
        PermissionsUtils.lacksPermissions(this, permissions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loadPic:

                Glide.with(this)
                        .load("http://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
                        .into(mImageView);
                break;
        }
    }
}
