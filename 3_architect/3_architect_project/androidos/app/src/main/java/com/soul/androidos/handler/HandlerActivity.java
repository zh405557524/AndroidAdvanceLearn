package com.soul.androidos.handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.soul.androidos.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * handler 框架测试类
 */
public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = HandlerActivity.class.getSimpleName();
    private  static Handler mHandler;

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
        thread.setName("test_main");
        thread.start();
        findViewById(R.id.bt_sendMsg).setOnClickListener(this);


    }

    /**
     * 模拟ActivityThread 主方法入口
     */
    public static void main(String[] args) {
        Log.i(TAG, "模拟ActivityThread ---- main");
        Log.i(TAG, "当前线程为:" + java.lang.Thread.currentThread().getName());
        Looper.prepareMainLooper();
        mHandler = new Handler() {
            @Override
            public void handlerMessage(Message msg) {
                super.handlerMessage(msg);
                Log.i(TAG, "handlerMessage_当前线程为:" + java.lang.Thread.currentThread().getName());
                Log.i(TAG, "handlerMessage_msg:" + msg.obj);
            }
        };
        Looper.loop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sendMsg://发送消息
                Message obtain = Message.obtain();
                obtain.obj = "测试 发送消息";
                Log.i(TAG, "onClick  bt_sendMsg 当前线程为:" + java.lang.Thread.currentThread().getName());
                mHandler.sendMessage(obtain);
                break;
        }
    }
}
