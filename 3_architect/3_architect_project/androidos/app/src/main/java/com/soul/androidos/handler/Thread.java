package com.soul.androidos.handler;

/**
 * Description: 线程
 * Author: 祝明
 * CreateDate: 2021/4/9 18:30
 * UpdateUser:
 * UpdateDate: 2021/4/9 18:30
 * UpdateRemark:
 */
public class Thread {

    public static Thread mThread;

    /**
     * 多线程共享变量 存储对象
     */
   static ThreadLocal.ThreadLocalMap threadLocals = null;


    public static synchronized Thread currentThread() {
        if (mThread == null) {
            mThread = new Thread();
        }
        return mThread;
    }

}
