package com.soul.androidos.pms;

import android.content.pm.PackageInfo;
import android.os.RemoteException;

import com.soul.androidos.IPackageManager;

/**
 * Description: 仿写 ApplicationPackageManager
 * Author: 祝明
 * CreateDate: 2021/5/12 9:29
 * UpdateUser:
 * UpdateDate: 2021/5/12 9:29
 * UpdateRemark:
 */
public class ApplicationPackageManager extends PackageManager {

    private IPackageManager mPM;

    public ApplicationPackageManager(IPackageManager PM) {
        mPM = PM;
    }

    @Override
    public PackageInfo getPackageInfo(String packageName, int flags) {
        try {
            return mPM.getPackageInfo(packageName, flags, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
