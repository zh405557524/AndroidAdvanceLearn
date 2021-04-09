package com.soul.androidos.handler;

/**
 * Description:循环器
 * Author: 祝明
 * CreateDate: 2021/4/9 13:59
 * UpdateUser:
 * UpdateDate: 2021/4/9 13:59
 * UpdateRemark:
 */
public class Looper {


    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal();

    /**
     * 消息队列
     */
    MessageQueue mQueue;


    public Looper(boolean quitAllowed) {

    }

    /**
     * 创建属于当前线程的looper
     */
    public static void prepare() {

    }

    /**
     * 创建looper
     *
     * @param quitAllowed 是否可以退出
     */
    public static void prepare(boolean quitAllowed) {

    }

    /**
     * 创建主线程的looper
     */
    public static void prepareMainLooper() {

    }


}
