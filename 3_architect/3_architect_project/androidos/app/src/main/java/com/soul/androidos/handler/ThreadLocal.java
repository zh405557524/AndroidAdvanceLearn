package com.soul.androidos.handler;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: 本地线程，多线程共享变量。
 * Author: 祝明
 * CreateDate: 2021/4/9 15:01
 * UpdateUser:
 * UpdateDate: 2021/4/9 15:01
 * UpdateRemark:
 */
public class ThreadLocal<T> {

    //线性探测散列映射
    private final int threadLocalHashCode = nextHashCode();
    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * The next hash code to be given out. Updated atomically. Starts at
     * zero.
     */
    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    /**
     * 获取线程变量
     *
     * @return
     */
    public T get() {
        ThreadLocalMap map = getMap();
        if (map == null) {
            map = new ThreadLocalMap(this, null);
            Thread.threadLocals = map;
        }
        ThreadLocalMap.Entry entry = map.getEntry(this);

        return (T) entry.value;
    }

    /**
     * 设置线程变量
     *
     * @param value
     */
    public void set(T value) {
        ThreadLocalMap map = getMap();
        if (map == null) {
            map = new ThreadLocalMap(this, value);
            Thread.threadLocals = map;
        }
        map.set(this, value);
    }

    public ThreadLocalMap getMap() {
        return Thread.threadLocals;
    }

    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    /**
     * 对象在 Thread 成员变量中，所以是在主内存中，可以线程共享
     */
    static class ThreadLocalMap {

        /**
         * 下次扩容的值
         */
        private int threshold;
        /**
         * 数组的初始大小
         */
        private static final int INITIAL_CAPACITY = 16;


        /**
         * 线程共享变量数组
         */
        private Entry[] table;

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


        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            setThreshold(INITIAL_CAPACITY);
        }

        /**
         * 设置扩容参数
         *
         * @param len
         */
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }


        /**
         * 通过threadLocal 对象拿到对应类的对象
         *
         * @param key
         */
        private Entry getEntry(ThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry entry = table[i];
            if (entry != null && entry.get() == key) {
                return entry;
            }
            //todo 继续查询
            return null;
        }

        private int size;

        /**
         * 存储 ThreadLocal 对象所在的类 的对象
         *
         * @param key
         * @param value
         */
        private void set(ThreadLocal<?> key, Object value) {
            //1、通过key 的hash值 找到对应的 下标。然后将value 设置进去
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len - 1);//获取key 的hash值,得出一个不超过len 的数值

            //1)、遍历 数组，找到对应的key ，如果key 存在 则 直接设置value；如果key 为null 索引 记作  staleSlot
            for (Entry e = tab[i]; e != null; e = tab[nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();
                if (k == key) {
                    e.value = value;
                    return;
                }
                if (k == null) {

                    replaceStaleEntry(key, value, i);
                    return;
                }
            }

            tab[i] = new Entry(key, value);
            int sz = ++size;
            if (cleanSomeSlots(i, sz) && sz >= threshold) {
                rehash();
            }

        }

        /**
         * 替换旧条目
         *
         * @param key
         * @param value
         * @param i
         */
        private void replaceStaleEntry(ThreadLocal<?> key, Object value, int i) {
            //todo 替换无效的条目

            // 2)、如果key为null 则 1、向数组前面获取key值，如果为null 则记录该角标 记作 slotToExpunge 。
            //   2、数组向后 获取k值，a、如果k与当前的key 值 相同，则 做新旧元素 替换 旧索引指向新值(新值为null) 新索引指向旧值，并删除旧索引 cleanSomeSlots()。
            //                                        b、如果k为null 并且  slotToExpunge 与 staleSlot 相同(就是1 记录的值 与 当前的索引相同 ) 则slotToExpunge 设置成为当前的index。
            //   3、

        }

        private void rehash() {
            //删除所有陈旧的条目
            expungeStateEntries();

            if (size >= threshold - threshold / 4) {
                resize();
            }
        }


        /**
         * 对数据组进行扩容
         */
        private void resize() {
            //todo 扩容

        }


        /**
         * 下一个索引
         *
         * @param i
         * @param len
         * @return
         */
        private int nextIndex(int i, int len) {

            return ((i + 1 < len) ? i + 1 : 0);
        }

        /**
         * 上一个索引
         *
         * @param i
         * @param len
         * @return
         */
        private int prevIndex(int i, int len) {

            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        /**
         * 清除旧条目
         *
         * @param i
         * @param n
         */
        private boolean cleanSomeSlots(int i, int n) {

            return false;
        }

        /**
         * 删除所有陈旧的条目
         */
        private void expungeStateEntries() {
            //todo 删除所有陈旧的条目

        }

    }
}
