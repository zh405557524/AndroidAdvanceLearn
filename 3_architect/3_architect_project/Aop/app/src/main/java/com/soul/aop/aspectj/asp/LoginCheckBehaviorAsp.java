package com.soul.aop.aspectj.asp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.soul.aop.aspectj.LoginActivity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Description: TODO
 * Author: zhuMing
 * CreateDate: 2021/3/23 19:18
 * ProjectName: Aop
 * UpdateUser:
 * UpdateDate: 2021/3/23 19:18
 * UpdateRemark:
 */
@Aspect
public class LoginCheckBehaviorAsp {

    public static String TAG = LoginCheckBehaviorAsp.class.getSimpleName();

    @Pointcut("execution(@com.soul.aop.aspectj.annotation.LoginCheck * *(..))")
    public void methodPointCut() {

    }

    @Around("methodPointCut()")
    public Object jointPotion(ProceedingJoinPoint jointPoint) throws Throwable {
        Context context = (Context) jointPoint.getThis();

        if (true) {
            Log.e(TAG, "检测已登录");
            return jointPoint.proceed();
        } else {
            Log.e(TAG, "检测未登录");
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
        }

        return null;
    }
}
