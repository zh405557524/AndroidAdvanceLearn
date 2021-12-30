package com.soul.oldskin;

import android.app.Application;

import java.util.Observable;

/**
 * Description: 换肤引擎
 * Author: 祝明
 * CreateDate: 2021/12/29 10:06
 * UpdateUser:
 * UpdateDate: 2021/12/29 10:06
 * UpdateRemark:
 */
public class SkinEngine extends Observable {

    public static final String TAG = SkinEngine.class.getSimpleName();

    private Application application;

    public static SkinEngine getInstance() {
        return SkinEngineHolder.sInstance;
    }


    /**
     * 给Application初始化的
     */
    public void skinApplicationInit(Application application) throws IllegalAccessException {
        if (this.application != null) {
            throw new IllegalAccessException("init number > 1");
        }

        this.application = application;
        // 注册Activity生命周期监听，之前讲过AOP切面
        application.registerActivityLifecycleCallbacks(new SkinActivityLifecycleCallbacks());
        // 单例初始化对象
        SkinResources.applicationInit(application);
    }


    /**
     * 换肤
     *
     * @param skinPath 换肤本地地址
     */
    public void updateSkin(String skinPath) throws IllegalAccessException {
        SkinResources.getInstance().setSkinResources(application, skinPath);

        /**
         * 我是被观察者，通知所有的观察者
         * 点击换肤第一步：通知所有的观察者，需要换肤了
         */
        setChanged();
        notifyObservers();
    }


    private static class SkinEngineHolder {
        private static SkinEngine sInstance = new SkinEngine();
    }


}
