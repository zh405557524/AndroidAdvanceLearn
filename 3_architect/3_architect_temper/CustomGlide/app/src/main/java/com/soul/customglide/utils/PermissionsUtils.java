package com.soul.customglide.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * * @author soul
 *
 * @项目名:Compilations
 * @包名: com.soul.library.utils
 * @作者：祝明
 * @描述：TODO
 * @创建时间：2017/2/22 13:23
 */

public class PermissionsUtils {
    //申请权限的回调
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    public static void lacksPermissions(Activity activity, String... permissions) {
        ArrayList<String> strings = new ArrayList<>();
        for (String permission : permissions) {
            if (lacksPermission(activity, permission)) {
                strings.add(permission);
            }
        }
        String[] strings1 = new String[strings.size()];
        String[] strings2 = strings.toArray(strings1);
        openPermissions(activity, strings2);
    }

    private static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }


    private static void openPermissions(Activity context, String[] strings) {
        if (strings != null && strings.length > 0) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, strings,
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

}
