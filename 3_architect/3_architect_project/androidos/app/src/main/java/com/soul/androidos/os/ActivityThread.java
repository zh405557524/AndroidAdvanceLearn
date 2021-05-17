package com.soul.androidos.os;

import android.os.IBinder;
import android.util.Log;

import com.soul.androidos.IPackageManager;
import com.soul.androidos.handler.Handler;
import com.soul.androidos.handler.Looper;
import com.soul.androidos.handler.Message;
import com.soul.androidos.os.service.ServiceManager;

/**
 * Description: 仿写 ActivityThread
 * Author: 祝明
 * CreateDate: 2021/5/12 9:48
 * UpdateUser:
 * UpdateDate: 2021/5/12 9:48
 * UpdateRemark:
 */
public class ActivityThread {

    private static Handler mHandler;
    private static IPackageManager sPackageManager;


    public static void main(String[] args) {
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


    public static IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            //Slog.v("PackageManager", "returning cur default = " + sPackageManager);
            return sPackageManager;
        }
        IBinder b = ServiceManager.getService("package");
        //Slog.v("PackageManager", "default service binder = " + b);
        sPackageManager = IPackageManager.Stub.asInterface(b);
        //Slog.v("PackageManager", "default service = " + sPackageManager);
        return sPackageManager;
    }

}
