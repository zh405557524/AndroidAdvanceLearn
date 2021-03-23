package com.soul.aop.aspectj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.soul.aop.R;
import com.soul.aop.aspectj.annotation.ClickBehavior;
import com.soul.aop.aspectj.annotation.LoginCheck;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 切面编程
 */
public class AspectjActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = AspectjActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectj);
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_area).setOnClickListener(this);
        findViewById(R.id.bt_coupon).setOnClickListener(this);
        findViewById(R.id.bt_score).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login://登录
                login();
                break;
            case R.id.bt_area://我的专区
                area();
                break;
            case R.id.bt_coupon://优惠券
                coupon();
                break;
            case R.id.bt_score://我的积分
                score();
                break;
        }
    }

    @ClickBehavior("登录")
    private void login() {
        Log.e(TAG, "模拟接口请求……验证通过，登录成功！");
    }

    @ClickBehavior("我的专区")
    @LoginCheck
    private void area() {
        Log.e(TAG, "开始跳转到 -> 我的专区 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @ClickBehavior("我的优惠券")
    @LoginCheck
    private void coupon() {
        Log.e(TAG, "开始跳转到 -> 我的优惠券 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @ClickBehavior("我的积分")
    @LoginCheck
    private void score() {
        Log.e(TAG, "开始跳转到 -> 我的积分 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }


}
