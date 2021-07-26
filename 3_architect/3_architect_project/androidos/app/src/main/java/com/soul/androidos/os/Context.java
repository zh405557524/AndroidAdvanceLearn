package com.soul.androidos.os;

import android.content.Intent;
import android.os.Bundle;

import com.soul.androidos.pms.ApplicationPackageManager;
import com.soul.androidos.pms.PackageManager;

/**
 * Description: 仿写 Context
 * Author: 祝明
 * CreateDate: 2021/5/12 9:29
 * UpdateUser:
 * UpdateDate: 2021/5/12 9:29
 * UpdateRemark:
 */
public abstract class Context {

    /**
     * 获取PMS
     *
     * @return
     */
    public PackageManager getPackageManager() {
        return new ApplicationPackageManager(ActivityThread.getPackageManager());
    }

    /**
     * 启动Activity
     *
     * @param intent
     */
    public abstract void startActivity(Intent intent);

    /**
     * 启动Activity
     *
     * @param intent
     */
    public abstract void startActivity(Intent intent, Bundle options);
}
