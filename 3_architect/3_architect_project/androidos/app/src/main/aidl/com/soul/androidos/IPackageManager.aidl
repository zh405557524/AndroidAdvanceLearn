// IPackageManager.aidl
package com.soul.androidos;

// Declare any non-default types here with import statements
import android.content.pm.PackageInfo;

interface IPackageManager {
   PackageInfo getPackageInfo(String packageName, int flags, int userId);
}
