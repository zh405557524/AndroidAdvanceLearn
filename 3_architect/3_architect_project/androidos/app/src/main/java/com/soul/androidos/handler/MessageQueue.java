package com.soul.androidos.handler;

/**
 * Description: 消息队列
 * Author: 祝明
 * CreateDate: 2021/4/9 13:59
 * UpdateUser:
 * UpdateDate: 2021/4/9 13:59
 * UpdateRemark:
 */
public class MessageQueue {

    // True if the message queue can be quit.
    private final boolean mQuitAllowed;

    /**
     * 消息池
     */
    Message mMessages;

    public MessageQueue(boolean quitAllowed) {
        mQuitAllowed = quitAllowed;
    }

    /**
     * 存入消息
     *
     * @param msg
     * @param when
     * @return
     */
    boolean enqueueMessage(Message msg, long when) {
        //存入链表头
        msg.when = when;
        msg.next = mMessages;
        mMessages = msg;
        notify();
        return true;
    }

    /**
     * 获取消息
     *
     * @return
     */
    Message next() {
        Message message;
        if (mMessages == null) {
            //todo 等待被唤醒
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        message = mMessages;
        mMessages = mMessages.next;
        return message;
    }


    /**
     * 退出消息队列
     *
     * @param safe
     */
    void quit(boolean safe) {

    }


}
