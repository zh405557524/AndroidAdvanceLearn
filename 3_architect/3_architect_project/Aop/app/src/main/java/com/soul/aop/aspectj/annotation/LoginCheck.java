package com.soul.aop.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: TODO
 * Author: zhuMing
 * CreateDate: 2021/3/23 19:17
 * ProjectName: Aop
 * UpdateUser:
 * UpdateDate: 2021/3/23 19:17
 * UpdateRemark:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
}
