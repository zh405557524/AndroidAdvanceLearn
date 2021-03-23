package com.soul.aop.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 用户点击痕迹
 * Author: zhuMing
 * CreateDate: 2021/3/22 9:12
 * ProjectName: Aop
 * UpdateUser:
 * UpdateDate: 2021/3/22 9:12
 * UpdateRemark:
 */
@Target(ElementType.METHOD)//目标作用在方法之上
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickBehavior {
    String value();
}
