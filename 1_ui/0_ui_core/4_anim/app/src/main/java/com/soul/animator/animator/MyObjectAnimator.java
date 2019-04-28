package com.soul.animator.animator;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/26 上午10:58
 * UpdateUser:
 * UpdateDate: 2019/4/26 上午10:58
 * UpdateRemark:
 */
public class MyObjectAnimator implements VSYNCManger.AnimationFrameCallback {

    private static final String TAG = "tuch";
    private long mStartTime = -1;
    private long mDuration = 0;
    private WeakReference<View> target;

    MyFloatPropertyValuesHolder mMyFloatPropertyValuesHolder;
    private long mStartTime1;

    private int index;

    public MyObjectAnimator(View view, String propertyName, float... values) {
        target = new WeakReference<View>(view);
        mMyFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }


    public static MyObjectAnimator ofFloat(View view, String propertyName, float... values) {

        final MyObjectAnimator anim = new MyObjectAnimator(view, propertyName, values);

        return anim;
    }

    public void start() {
        mMyFloatPropertyValuesHolder.setupSetter(target);
        mStartTime1 = System.currentTimeMillis();
        VSYNCManger.getInstance().add(this);
        //初始值

    }


    //每隔16ms 执行一次
    @Override
    public boolean doAnimationFrame(long currentTime) {
        final long total = mDuration / 16;

        //执行百分比


        return false;
    }
}
















































