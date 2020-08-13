package com.soul.ffmpeg.system;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import java.io.File;

/*

 */
public final class Global {
    private static final String EXTERNAL_DATA_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator + "autoai_vos" + File.separator;
    private static final String INNER_DATA_DIR = File.separator + "data" + File.separator;


    private static Context sContext = null;
    private static Handler sUiHandler = null;
    private static Handler sBgHandler = null;
    private static Handler sHeavyHandler = null;

    public static void setContext(Context context) {
        sContext = context;
    }

    public static void setHandler(Handler uiHandler, Handler bgHandler, Handler heavyHandler) {
        sUiHandler = uiHandler;
        sBgHandler = bgHandler;
        sHeavyHandler = heavyHandler;
    }

    /*
        获取全局Context
     */
    public static Context getContext() {
        return sContext;
    }


    public static String getExternalDataDir() {
        return EXTERNAL_DATA_DIR;
    }

    public static String getInnerDataDir() {
        if (sContext == null) {
            throw new RuntimeException("Global context is not seted ");
        }
        return sContext.getApplicationInfo().dataDir + INNER_DATA_DIR;
    }


    /*
        post任务到工作线程中执行,主流程任务使用该方法，耗时的任务不要使用该方法
     */
    public static void postBgTaskDelayed(Runnable oTask, long delayMills) {
        if (sBgHandler != null) {
            sBgHandler.postDelayed(oTask, delayMills);
        }
    }

    /*
     post任务到UI线程中执行
     */
    public static void postUiTaskDelayed(Runnable oTask, long delayMills) {
        if (sUiHandler != null) {
            sUiHandler.postDelayed(oTask, delayMills);
        }
    }

    /*
        非紧急任务，并且耗时较大的任务，使用该方法执行
     */
    public static void postHeavyTaskDelayed(Runnable oTask, long delayMills) {
        if (sHeavyHandler != null) {
            sHeavyHandler.postDelayed(oTask, delayMills);
        }
    }

    /**
     * 获取ui handler
     *
     * @return Handler
     */
    public static Handler getUiHandler() {

        return sUiHandler;
    }


    public static Handler getBgHandler() {
        if (sBgHandler != null) {
            return sBgHandler;
        }
        return new Handler();
    }

}