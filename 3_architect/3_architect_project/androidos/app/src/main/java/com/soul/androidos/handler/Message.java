package com.soul.androidos.handler;

/**
 * Description: 消息
 * Author: 祝明
 * CreateDate: 2021/4/9 13:59
 * UpdateUser:
 * UpdateDate: 2021/4/9 13:59
 * UpdateRemark:
 */
public class Message {

    /**
     * 消息类型
     */
    public int what;

    /**
     * 参数 1
     */
    public int arg1;

    /**
     * 参数 2
     */
    public int arg2;

    /**
     * 参数 object
     */
    public Object obj;

    int flags;

    /**
     * 当前消息的时间
     */
    long when;

    Handler target;

    Runnable callback;

    /**
     * 链表结构-关联下一个message对象
     */
    Message next;

    /**
     * 对象锁
     *
     * @hide
     */
    public static final Object sPoolSync = new Object();


    /**
     * 消息池
     */
    private static Message sPoll;

    /**
     * 消息池大小
     */
    private static int sPoolSize = 0;


    /**
     * 消息池的最大值
     */
    private static final int MAX_POOL_SIZE = 50;


    /**
     * 复用一个消息
     *
     * @return
     */
    public static Message obtain() {
        synchronized (sPoolSync) {//锁
            if (sPoll != null) {
                //当消息池 不为空的时候,从链表头取出该对象
                Message message = sPoll;
                sPoll = message.next;
                message.next = null;
                message.flags = 0;//清楚标记
                sPoolSize--;
                return message;
            }
        }
        return new Message();
    }


    /**
     * 获取当前消息发送的时间
     *
     * @return
     */
    public long getWhen() {
        return when;
    }

    /**
     * 关联handler
     *
     * @param target Handler
     */
    public void setTarget(Handler target) {
        this.target = target;
    }

    /**
     * 获取关联的handler
     *
     * @return
     */
    public Handler getTarget() {
        return target;
    }


    public Runnable getCallback() {
        return callback;
    }

    public Message setCallback(Runnable callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 将message 设置给handler
     */
    public void sendToTarget() {
        target.sendMessage(this);
    }

    /**
     * 释放message 资源
     */
    public void recycle() {

    }

    /**
     * 释放message 资源，并将当前对象添加消息池中
     */
    void recycleUnchecked() {
        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                //将当前message 添加到链表头
                next = sPoll;
                sPoll = this;
                sPoolSize++;
            }
        }

    }

}
