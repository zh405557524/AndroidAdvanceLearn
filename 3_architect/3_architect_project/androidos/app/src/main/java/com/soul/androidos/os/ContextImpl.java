package com.soul.androidos.os;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.AndroidRuntimeException;
import android.view.Display;

import com.soul.androidos.pms.LoadedApk;

/**
 * Description: 仿写 ContextImpl
 * Author: 祝明
 * CreateDate: 2021/6/7 16:20
 * UpdateUser:
 * UpdateDate: 2021/6/7 16:20
 * UpdateRemark:
 */
public class ContextImpl extends Context {


    private ActivityThread mMainThread;

    static ContextImpl createAppContext(ActivityThread mainThread, LoadedApk packageInfo) {
        if (packageInfo == null)
            throw new IllegalArgumentException("packageInfo");
        return new ContextImpl(null, mainThread,
                packageInfo, null, null, false, null, null);
    }

    //创建上下文
    static ContextImpl createActivityContext(ActivityThread mainThread,
                                             LoadedApk packageInfo, IBinder activityToken) {
        if (packageInfo == null)
            throw new IllegalArgumentException("packageInfo");
        if (activityToken == null)
            throw new IllegalArgumentException("activityInfo");
        return new ContextImpl(null, mainThread,
                packageInfo, activityToken, null, false, null, null);
    }

    private ContextImpl(ContextImpl container, ActivityThread mainThread,
                        LoadedApk packageInfo, IBinder activityToken, UserHandle user, boolean restricted,
                        Display display, Configuration overrideConfiguration) {

    }

    /**
     * 1、启动Activity
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(Intent intent, Bundle options) {

        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) == 0) {//判断当前的activity 的启动模式是否是新的task
            throw new AndroidRuntimeException(
                    "Calling startActivity() from outside of an Activity "
                            + " context requires the FLAG_ACTIVITY_NEW_TASK flag."
                            + " Is this really what you want?");
        }

        mMainThread.getInstrumentation().execStartActivity(getOuterContext(), mMainThread.getApplicationThread(), null, (Activity) null, intent, -1, options);
    }

    private Context getOuterContext() {
        return this;
    }

}
