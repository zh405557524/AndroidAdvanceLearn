package com.soul.animator.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/26 上午11:40
 * UpdateUser:
 * UpdateDate: 2019/4/26 上午11:40
 * UpdateRemark:
 */
public class MyFloatPropertyValuesHolder {

    //属性名
    String mPropertyName;
    //float
    Class mValueType;

    MyKeyFrameSet mKeyFrameSets;
    Method mSetter = null;


    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;
        mKeyFrameSets = MyKeyFrameSet.ofFloat(values);
    }


    public void setupSetter(WeakReference<View> target) {
        final char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        final String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;
        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


}
