package com.soul.androidos.handler;

import android.os.Bundle;
import android.util.Log;

import com.soul.androidos.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * handler 框架测试类
 */
public class HandlerActivity extends AppCompatActivity {

    public static String TAG = HandlerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        java.lang.Thread thread = new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                main(null);
            }
        });
        thread.start();
    }

    /**
     * 模拟ActivityThread 主方法入口
     */
    public static void main(String[] args) {
        Log.i(TAG, "模拟ActivityThread ---- main");
        Looper.prepareMainLooper();
    }
}
