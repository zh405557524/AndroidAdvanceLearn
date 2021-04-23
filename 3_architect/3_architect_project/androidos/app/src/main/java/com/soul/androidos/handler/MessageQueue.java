package com.soul.androidos.handler;

import android.os.SystemClock;

/**
 * Description: 消息队列
 * Author: 祝明
 * CreateDate: 2021/4/9 13:59
 * UpdateUser:
 * UpdateDate: 2021/4/9 13:59
 * UpdateRemark:
 */
public class MessageQueue {


    /**
     * native对象
     */
    private final long mPtr;


    // True if the message queue can be quit.
    private final boolean mQuitAllowed;

    /**
     * 消息池
     */
    Message mMessages;

    public MessageQueue(boolean quitAllowed) {
        mQuitAllowed = quitAllowed;
        mPtr = nativeInit();
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
        nativeWake(mPtr);
        return true;
    }


    /**
     * 线程下一次 被唤醒的时间；不需要唤醒 则为-1
     */
    private int nextPollTimeoutMillis = -1;

    /**
     * 获取消息
     *
     * @return
     */
    Message next() {
        Message message;
        if (mMessages == null) {
            nextPollTimeoutMillis = -1;
            isSloop = true;
            nativePollOnce(mPtr, nextPollTimeoutMillis);
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

    /**
     * 初始化
     *
     * @return
     */
    private static long nativeInit() {
        return 0;
    }

    private boolean isSloop;

    /**
     * 等待下一次消息
     *
     * @param ptr
     * @param timeoutMillis 等待的时间，-1 就一直等待
     */
    private void nativePollOnce(long ptr, int timeoutMillis) {
        while (isSloop) {
            SystemClock.sleep(1);
        }
    }

    /**
     * 将线程唤醒
     *
     * @param ptr
     */
    private void nativeWake(long ptr) {
        isSloop = false;
    }

}
