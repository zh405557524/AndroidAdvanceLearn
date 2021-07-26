package com.soul.androidos.ams;

import android.os.Binder;
import android.os.IBinder;

/**
 * Description: ActivityManager 本地代理类
 * Author: 祝明
 * CreateDate: 2021/6/10 9:31
 * UpdateUser:
 * UpdateDate: 2021/6/10 9:31
 * UpdateRemark:
 */
public class ActivityManagerNative extends Binder implements IActivityManager {

    @Override
    public IBinder asBinder() {
        return null;
    }

}
