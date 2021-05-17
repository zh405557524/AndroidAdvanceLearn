package com.soul.androidos.os;

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
public class Context {

    public PackageManager getPackageManager() {
        return new ApplicationPackageManager(ActivityThread.getPackageManager());
    }

}
