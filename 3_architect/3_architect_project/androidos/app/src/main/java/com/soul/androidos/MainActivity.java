package com.soul.androidos;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.soul.androidos.handler.HandlerActivity;
import com.soul.androidos.os.ActivityThread;
import com.soul.androidos.os.boot.SystemServer;
import com.soul.androidos.os.service.ServiceManager;
import com.soul.androidos.pms.PMSTestActivity;

import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LinkedList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_handler).setOnClickListener(this);
        findViewById(R.id.bt_pms).setOnClickListener(this);
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.init(MainActivity.this, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //系统启动
                SystemServer.main(null);
                ActivityThread.systemMain();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_handler://handler 测试
                startActivity(new Intent(MainActivity.this, HandlerActivity.class));
                break;
            case R.id.bt_pms://pms 测试
                startActivity(new Intent(MainActivity.this, PMSTestActivity.class));
                break;
        }
    }

}
