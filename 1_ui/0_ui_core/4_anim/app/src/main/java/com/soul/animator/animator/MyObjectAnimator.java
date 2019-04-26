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
public class MyObjectAnimator {

    private static final String TAG = "tuch";
    private long mStartTime = -1;
    private long mDuration = 0;
    private WeakReference<View> target;

    MyFloatPropertyValuesHolder mMyFloatPropertyValuesHolder;

    public MyObjectAnimator(View view, String propertyName, float... values) {
        target = new WeakReference<View>(view);
        mMyFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }


    public static MyObjectAnimator ofFloat(View view, String propertyName, float... values) {

        final MyObjectAnimator anim = new MyObjectAnimator(view, propertyName, values);

        return anim;
    }


}
















































