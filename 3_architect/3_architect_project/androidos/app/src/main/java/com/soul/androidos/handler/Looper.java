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
     * 主线程的looper
     */
    private static Looper sMainLooper;

    /**
     * 消息队列
     */
    MessageQueue mQueue;


    public Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);
    }

    /**
     * 创建属于当前线程的looper
     */
    public static void prepare() {
        prepare(true);
    }

    /**
     * 创建looper
     *
     * @param quitAllowed 是否可以退出
     */
    public static void prepare(boolean quitAllowed) {
        Looper looper = sThreadLocal.get();
        if (looper != null) {
            throw new RuntimeException("looper already init");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }

    /**
     * 创建主线程的looper
     */
    public static void prepareMainLooper() {
        prepare(false);
        sMainLooper = sThreadLocal.get();
    }


    public static Looper getMainLooper() {
        return sMainLooper;
    }


    /**
     * 开启循环
     */
    public void loop() {
        for (; ; ) {
            Message next = mQueue.next();
            Handler target = next.target;
            target.dispatchMessage(next);
        }
    }


    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 退出循环
     */
    public void quit() {
        //todo 退出机制

    }

    /**
     * 安全退出循环
     */
    public void quitSafely() {

    }

    public MessageQueue getQueue() {
        return mQueue;
    }

}
