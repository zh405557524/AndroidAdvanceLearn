package com.soul.aop.aspectj.asp;

import android.util.Log;

import com.soul.aop.aspectj.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Description: 点击事件 切面类
 * Author: zhuMing
 * CreateDate: 2021/3/22 9:27
 * ProjectName: Aop
 * UpdateUser:
 * UpdateDate: 2021/3/22 9:27
 * UpdateRemark:
 */
@Aspect
public class ClickBehaviorAsp {
    public static String TAG = ClickBehaviorAsp.class.getSimpleName();


    /***
     *应用中用到了哪些注解，放到当前的切入点进行处理(找到需要处理的切入点)
     * execution,以方法执行时作为切点，触发Aspect类
     * *(..))可以处理ClickBehavior这个类所有方法
     */
    @Pointcut("execution(@com.soul.aop.aspectj.annotation.ClickBehavior * *(..))")
    public void methodPointCut() {
    }

    /**
     * 2、对切入点如何处理
     *
     * @param joinPoint
     * @return
     */
    @Around("methodPointCut()")
    public Object jointPotion(ProceedingJoinPoint joinPoint) throws Throwable {

        //获取签名方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法所属类名
        String className = signature.getDeclaringType().getSimpleName();

        //获取方法名
        String methodName = signature.getName();
        //获取方法的注解值(需要统计的用户行为)
        String funName = signature.getMethod().getAnnotation(ClickBehavior.class).value();

        // 统计方法的执行时间、统计用户点击某功能行为。
        long begin = System.currentTimeMillis();
        Log.e(TAG, "ClickBehavior Method Start >>> ");
        Object proceed = joinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG, "ClickBehavior Method End >>> ");
        Log.e(TAG, String.format("统计了:%s功能,在%s类的%s方法，用时%d ms", funName, className, methodName, duration));

        return proceed;
    }

}
