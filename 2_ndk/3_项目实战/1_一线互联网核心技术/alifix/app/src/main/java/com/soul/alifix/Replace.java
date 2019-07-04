package com.soul.alifix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-01 17:42
 * UpdateUser:
 * UpdateDate: 2019-07-01 17:42
 * UpdateRemark:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Replace {

    String clazz();//修复哪一个class

    String method();//method
}
