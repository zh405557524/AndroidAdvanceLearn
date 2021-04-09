package com.soul.androidos.handler;

import java.lang.ref.WeakReference;

/**
 * Description: 本地线程，多线程共享变量。
 * Author: 祝明
 * CreateDate: 2021/4/9 15:01
 * UpdateUser:
 * UpdateDate: 2021/4/9 15:01
 * UpdateRemark:
 */
public class ThreadLocal<T> {


    /**
     * 获取线程变量
     *
     * @return
     */
    public T get() {
        return null;
    }

    /**
     * 设置线程变量
     *
     * @param value
     */
    public void set(T value) {

    }


    /**
     * 对象在 Thread 成员变量中，所以是在主内存中，可以线程共享
     */
    static class ThreadLocalMap {

        /**
         * 数组的初始大小
         */
        private static final int INITIAL_CAPACITY = 16;


        static class Entry extends WeakReference<ThreadLocal<?>> {
            /**
             * The value associated with this ThreadLocal.
             */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }


        private Entry[] table;

        /**
         * 通过threadLocal 对象拿到对应类的对象
         *
         * @param key
         */
        private void getEntry(ThreadLocal<?> key) {

        }

        /**
         * 存储 ThreadLocal 对象所在的类 的对象
         *
         * @param key
         * @param value
         */
        private void set(ThreadLocal<?> key, Object value) {

        }

    }
}
