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

    public static String TAG = Handler.class.getSimpleName();

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
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(Message message) {
        sendMessageAtTime(message, System.currentTimeMillis());
    }

    public void sendMessage(Message message, long delayMillis) {
        sendMessageAtTime(message, System.currentTimeMillis() + delayMillis);
    }

    public void post(Runnable r) {
        Message message = new Message();
        message.callback = r;
        sendMessage(message);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        Message message = new Message();
        message.callback = r;
        sendMessageAtTime(message, System.currentTimeMillis() + delayMillis);
    }

    /**
     * 消息处理
     *
     * @param msg
     */
    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            msg.callback.run();
        } else {
            handlerMessage(msg);
        }
    }

    /**
     * 消息处理，子类实现
     *
     * @param msg 消息
     */
    public void handlerMessage(Message msg) {

    }

    /**
     * 发送消息
     *
     * @param message      消息
     * @param uptimeMillis 发送的时间
     */
    private void sendMessageAtTime(Message message, long uptimeMillis) {
        message.target = this;
        boolean b = mQueue.enqueueMessage(message, uptimeMillis);
    }


}
