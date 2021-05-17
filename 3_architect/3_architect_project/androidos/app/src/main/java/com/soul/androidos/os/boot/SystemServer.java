package com.soul.androidos.os.boot;


import com.soul.androidos.os.Context;
import com.soul.androidos.pms.Installer;
import com.soul.androidos.pms.PackageManagerService;

/**
 * Description: 模拟 系统启动类
 * Author: 祝明
 * CreateDate: 2021/5/17 10:27
 * UpdateUser:
 * UpdateDate: 2021/5/17 10:27
 * UpdateRemark:
 */
public class SystemServer {

    private Context mSystemContext;
    private Installer mInstaller;
    private boolean mOnlyCore;
    private PackageManagerService mPackageManagerService;

    public static void main(String[] args) {
        new SystemServer().run();
    }

    private void run() {
        //启动服务 创建 pms、ams等
        startBootstrapServices();
    }

    /**
     * 启动服务 创建 pms、ams等
     */
    private void startBootstrapServices() {
        mPackageManagerService = PackageManagerService.main(mSystemContext, mInstaller, false, mOnlyCore);
    }
}
