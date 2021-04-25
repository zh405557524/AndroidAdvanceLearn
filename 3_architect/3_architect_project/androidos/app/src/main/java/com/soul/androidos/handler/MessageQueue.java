package com.soul.androidos.handler;

import android.os.SystemClock;
import android.util.Log;

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
     * 获取消息
     *
     * @return
     */
    Message next() {
        //        线程下一次 被唤醒的时间；不需要唤醒 则为 -1
        int nextPollTimeoutMillis = 0;
        for (; ; ) {
            isSloop = true;
            nativePollOnce(mPtr, nextPollTimeoutMillis);
            Message message = mMessages;
            if (message != null && message.target != null) {
                nextPollTimeoutMillis = (int) (message.when - System.currentTimeMillis());
                Log.i("TAG", "nextPollTimeoutMillis:" + nextPollTimeoutMillis);
                if (nextPollTimeoutMillis <= 0) {
                    mMessages = message.next;
                    return message;
                }
            } else {
                nextPollTimeoutMillis = -1;
            }
        }
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
        long timeOut = System.currentTimeMillis() + timeoutMillis;
        while (isSloop) {
            if (timeoutMillis != -1 && timeOut <= System.currentTimeMillis()) {
                isSloop = false;
                break;
            }
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
