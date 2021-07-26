package com.soul.androidos.os;

import android.os.IBinder;
import android.util.Log;

import com.soul.androidos.IPackageManager;
import com.soul.androidos.ams.Instrumentation;
import com.soul.androidos.handler.Handler;
import com.soul.androidos.handler.Looper;
import com.soul.androidos.handler.Message;
import com.soul.androidos.os.service.ServiceManager;
import com.soul.androidos.pms.LoadedApk;

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

    //将从AMS接收到的RPC数据通过消息，传递给ActivityThread
    final ApplicationThread mAppThread = new ApplicationThread();


    /**
     * ------------------------------与源码无关 start--------------------------------
     */
    private static ContextImpl sContext;

    /**
     * ------------------------------与源码无关 end--------------------------------
     */


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

    public static ActivityThread systemMain() {
        // The system process on low-memory devices do not get to use hardware
        // accelerated drawing, since this can add too much overhead to the
        // process.

        ActivityThread thread = new ActivityThread();
        thread.attach(true);
        return thread;
    }

    public ApplicationThread getApplicationThread() {
        return mAppThread;
    }


    static final class ActivityClientRecord {

    }


    public Instrumentation getInstrumentation() {
        /**------------------------------与源码无关 start--------------------------------*/
        return new Instrumentation();
        /**------------------------------与源码无关 end--------------------------------*/

    }

    /**
     * ------------------------------与源码无关 start--------------------------------
     */

    //向AMS发送启动完成消息
    private void attach(boolean system) {
        sContext = ContextImpl.createAppContext(this, new LoadedApk());
    }


    public static Context getContext() {
        return sContext;
    }

    /**------------------------------与源码无关 end--------------------------------*/

    //ApplicationThread是ActivityThread的内部类，将从AMS接收到的RPC数据通过消息，传递给ActivityThread
    private class ApplicationThread extends ApplicationThreadNative {

        @Override
        public IBinder asBinder() {
            return null;
        }
    }


}
