package com.soul.customglide.glide.cache;

import com.soul.customglide.glide.resource.Value;
import com.soul.customglide.glide.resource.ValueCallback;
import com.soul.customglide.utils.Tool;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description：活动缓存---真是被使用的资源
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/3 15:47
 */
public class ActiveCache {


    private Map<String, WeakReference<Value>> mapList = new HashMap<>();
    private ReferenceQueue<Value> mQueue;
    private Thread mThread;
    private boolean isCloseThread;
    private boolean isShoudonRemove;
    private ValueCallback mValueCallback;

    public ActiveCache(ValueCallback valueCallback) {
        mValueCallback = valueCallback;
    }

    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);
        value.setCallback(mValueCallback);
        mapList.put(key, new CustomWeakReference(value, getQueue(), key));
    }

    public Value get(String key) {
        WeakReference<Value> valueWeakReference = mapList.get(key);
        if (null != valueWeakReference) {
            return valueWeakReference.get();
        }
        return null;
    }


    public Value remove(String key) {
        isShoudonRemove = true;
        WeakReference<Value> valueWeakReference = mapList.remove(key);
        if (null != valueWeakReference) {
            return valueWeakReference.get();
        }
        return null;
    }


    public void closeThread() {
        isCloseThread = true;
        if (null != mThread) {
            mThread.interrupt();
            try {
                //线程稳定安全 停下来
                mThread.join(TimeUnit.SECONDS.toMillis(5));
                if (mThread.isAlive()) {
                    throw new IllegalStateException("活动缓存中 关闭线程 线程没有停下来");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private ReferenceQueue<? super Value> getQueue() {
        mQueue = new ReferenceQueue<>();

        //监听这个弱引用 是否被回收了
        mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (!isCloseThread) {

                    try {
                        //mQueue.remove(); 阻塞式的方法
                        Reference<? extends Value> remove = mQueue.remove();
                        CustomWeakReference weakReference = (CustomWeakReference) remove;
                        //从容器中移除
                        if (mapList != null && !mapList.isEmpty() && !isShoudonRemove) {
                            mapList.remove(weakReference.key);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        return null;
    }


    public class CustomWeakReference extends WeakReference<Value> {

        private String key;

        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> q, String key) {
            super(referent, q);
            this.key = key;
        }
    }


}
