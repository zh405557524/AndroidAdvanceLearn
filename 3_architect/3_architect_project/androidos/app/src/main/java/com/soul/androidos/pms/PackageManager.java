package com.soul.androidos.pms;

import android.content.pm.PackageInfo;

/**
 * Description: 仿写 PackageManager
 * Author: 祝明
 * CreateDate: 2021/5/12 9:30
 * UpdateUser:
 * UpdateDate: 2021/5/12 9:30
 * UpdateRemark:
 */
public abstract class PackageManager {
    /**
     * 获取包名信息
     *
     * @param packageName
     * @param flags
     * @return
     */
    public abstract PackageInfo getPackageInfo(String packageName, int flags);
}
