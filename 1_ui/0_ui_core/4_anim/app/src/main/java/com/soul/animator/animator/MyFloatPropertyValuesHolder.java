package com.soul.animator.animator;

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

    Method mSetter = null;


    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;
        mkeyframes = MyKeyFrameSet.ofFLoat(values);
    }


}
