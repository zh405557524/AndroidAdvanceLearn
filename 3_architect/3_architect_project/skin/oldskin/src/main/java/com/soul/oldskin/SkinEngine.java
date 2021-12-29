package com.soul.oldskin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Description: 换肤引擎
 * Author: 祝明
 * CreateDate: 2021/12/29 10:06
 * UpdateUser:
 * UpdateDate: 2021/12/29 10:06
 * UpdateRemark:
 */
public class SkinEngine {

    public static final String TAG = SkinEngine.class.getSimpleName();

    public static SkinEngine getInstance() {
        return SkinEngineHolder.sInstance;
    }

    /**
     * 换肤
     *
     * @param skinPath 换肤本地地址
     */
    public void updateSkin(String skinPath) throws IllegalAccessException {



    }


    private static class SkinEngineHolder {
        private static SkinEngine sInstance = new SkinEngine();
    }


}
