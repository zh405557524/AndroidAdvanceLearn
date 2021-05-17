package com.soul.androidos.pms;

import android.content.pm.PackageInfo;
import android.os.RemoteException;

import com.soul.androidos.IPackageManager;
import com.soul.androidos.os.Context;
import com.soul.androidos.os.service.ServiceManager;

/**
 * Description: 仿写 pms 安装包管理服务
 * Author: 祝明
 * CreateDate: 2021/5/12 9:30
 * UpdateUser:
 * UpdateDate: 2021/5/12 9:30
 * UpdateRemark:
 */
public class PackageManagerService extends IPackageManager.Stub {


    public PackageManagerService(Context context, Installer installer,
                                 boolean factoryTest, boolean onlyCore) {

    }

    //SystemServer调用main
    public static final PackageManagerService main(Context context, Installer installer,
                                                   boolean factoryTest, boolean onlyCore) {
        //构造PMS对象
        PackageManagerService m = new PackageManagerService(context, installer, factoryTest, onlyCore);
        //注册到ServiceManager中
        ServiceManager.addService("package", m);
        return m;
    }

    @Override
    public PackageInfo getPackageInfo(String packageName, int flags, int userId) throws RemoteException {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.packageName = "com.soul.bbbb";
        return packageInfo;
    }
}
