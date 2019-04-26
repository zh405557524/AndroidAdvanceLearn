package com.soul.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/25 下午4:07
 * UpdateUser:
 * UpdateDate: 2019/4/25 下午4:07
 * UpdateRemark:
 */
public class ViewGroup extends View {

    List<View> childList = new ArrayList<>();

    private View[] mChildren = new View[0];

    private TouchTarget mFirstTouchTarget;

    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }


    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = childList.toArray(new View[childList.size()]);
    }

    //事件分发的入口
    public boolean dispatchTouchEvent(MotionEvent event) {

        boolean intercepted = onInterceptTouchEvent(event);
        final int actionMasked = event.getActionMasked();

        boolean handled = false;
        //TouchTarget 模式 内存缓存
        TouchTarget newTouchTarget = null;


        if (actionMasked == MotionEvent.ACTION_DOWN) {
            clearTouchTargets();
        }

        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted) {
            //down事件
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                final View[] children = mChildren;
                //耗时
                for (int i = children.length - 1; i >= 0; i--) {
                    final View child = children[i];
                    //View能够接受到事件
                    if (!child.isContainer(event.getX(), event.getY())) {
                        continue;
                    }
                    //能够接受事件,分发给他
                    if (dispatchTransformedToucheEvent(event, child)) {
                        // View【】采取message的方式进行 链表结构。
                        handled = true;
                        newTouchTarget = addTouchTarget(child);
                        break;
                    }
                }
            }
        }



        //当前的ViewGroup -->
        if (mFirstTouchTarget == null) {//down 事件未被消费
            handled = dispatchTransformedToucheEvent(event, null);
        } else {
            //down被消费或者move中
            //拿到View直接进行分发
            TouchTarget predecessor = null;
            TouchTarget target = mFirstTouchTarget;
            while (target != null) {
                final TouchTarget next = target.next;

                if (target == new TouchTarget()) {
                    //当时down事件时,只分发一次
                    handled = true;
                } else {
                    //move 事件,进行分发
                    if (dispatchTransformedToucheEvent(event, target.child)) {
                        handled = true;
                    }
                }
                //将当前的对象，指向next的内存地址
                target = next;
            }

        }

        if (actionMasked == MotionEvent.ACTION_CANCEL) {
            clearTouchTargets();
        }
        return handled;
    }

    private TouchTarget addTouchTarget(View child) {
        final TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;
    }


    //分发处理 子控件 View
    private boolean dispatchTransformedToucheEvent(MotionEvent event, View child) {
        boolean handled = false;
        if (child != null) {
            //当前View消费了
            handled = child.dispatchTouchEvent(event);
        } else {
            super.dispatchTouchEvent(event);
        }
        return handled;
    }


    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    private String name;

    public void setName(String string) {
        name = string;
    }

    @Override
    public String toString() {
        return "ViewGroup{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * Clears all touch targets.
     */
    private void clearTouchTargets() {
        TouchTarget target = mFirstTouchTarget;
        if (target != null) {
            do {
                TouchTarget next = target.next;
                target.recycle();
                target = next;
            } while (target != null);
            mFirstTouchTarget = null;
        }
    }

    private static final class TouchTarget {
        private View child;//当前缓存的View

        private static TouchTarget mRecycleBin;
        private TouchTarget next;
        //回收池
        private static final Object sRecycleLock = new Object[0];

        //size
        private static int sRecycledCount;

        public static TouchTarget obtain(View child) {
            TouchTarget target;
            synchronized (sRecycleLock) {
                if (mRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = mRecycleBin;
                }
                mRecycleBin = target.next;
                sRecycledCount--;
                target.next = null;

            }
            target.child = child;
            return target;
        }

        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("已经被回收过了");
            }
            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    next = mRecycleBin;
                    mRecycleBin = this;
                    sRecycledCount += 1;
                }
            }

        }


    }

}
















