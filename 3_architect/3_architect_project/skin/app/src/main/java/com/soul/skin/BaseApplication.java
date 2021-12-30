package com.soul.skin;

import android.app.Application;

import com.soul.oldskin.SkinEngine;

/**
 * Description: TODO
 * Author: 祝明
 * CreateDate: 2021/12/29 11:20
 * UpdateUser:
 * UpdateDate: 2021/12/29 11:20
 * UpdateRemark:
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            SkinEngine.getInstance().skinApplicationInit(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
