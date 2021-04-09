package com.soul.androidos.handler;

/**
 * Description: handler
 * Author: 祝明
 * CreateDate: 2021/4/9 13:58
 * UpdateUser:
 * UpdateDate: 2021/4/9 13:58
 * UpdateRemark:
 */
public class Handler {

    /**
     * 循环器
     */
    Looper mLooper;

    /**
     * 消息队列
     */
    MessageQueue mQueue;


    public interface Callback {

        boolean handlerMessage(Message msg);

    }


    public Handler() {

    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(Message message) {

    }

    public void sendMessage(Message message, long delayMillis) {

    }

    public void post(Runnable r) {

    }

    public void postDelayed(Runnable r, long delayMillis) {

    }

    /**
     * 消息处理
     *
     * @param msg
     */
    public void dispatchMessage(Message msg) {

    }

    /**
     * 消息处理，子类实现
     *
     * @param msg 消息
     */
    public void HandlerMessage(Message msg) {

    }

    /**
     * 发送消息
     *
     * @param message      消息
     * @param uptimeMillis 发送的时间
     */
    private void sendMessageAtTime(Message message, long uptimeMillis) {
        //todo 加入消息队列中

    }


}
