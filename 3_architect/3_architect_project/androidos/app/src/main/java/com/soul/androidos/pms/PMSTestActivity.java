package com.soul.androidos.pms;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.soul.androidos.R;
import com.soul.androidos.os.Context;
import com.soul.androidos.os.service.ServiceManager;

import androidx.appcompat.app.AppCompatActivity;

public class PMSTestActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = PMSTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmstest);
        findViewById(R.id.bt_getPackageInfo).setOnClickListener(this);
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.init(PMSTestActivity.this, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getPackageInfo:
                //仿写
                Context context = new Context();
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo("com.soul.xxxx", 0);
                Log.i(TAG, "packageInfo:" + packageInfo.packageName);
                //os

                //                android.content.Context baseContext = getBaseContext();
                //                android.content.pm.PackageManager packageManager1 = baseContext.getPackageManager();
                //                try {
                //                    packageManager1.getPackageInfo("com.soul.xxxx", 0);
                //                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                //                    e.printStackTrace();
                //                }
                break;
        }
    }
}
