package com.soul.customglide.glide.request;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.soul.customglide.glide.fragment.ActivityFragmentManger;
import com.soul.customglide.glide.fragment.FragmentActivityFragmentManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @Description：TODO
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 14:55
 */
public class RequestManger {


    private static final String ACTIVITY_NAME = "activity_name";
    private static final int NEXT_HANDLER_MSG = 9954;
    private static final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_NAME";
    private static RequestTargetEngine requestTargetEngine;

    static {
        requestTargetEngine = new RequestTargetEngine();
    }

    private Context requestManagerContext;

    /**
     * context application 无法做管理
     *
     * @param requestManagerContext
     */
    public RequestManger(Context requestManagerContext) {
        this.requestManagerContext = requestManagerContext;
    }

    /**
     * 可以管理生命周期
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public RequestManger(Activity activity) {
        this.requestManagerContext = activity;
        FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.Fragment fragment = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        if (fragment == null) {
            fragment = new ActivityFragmentManger(requestTargetEngine);
            //添加到管理器中
            fragmentManager.beginTransaction().add(fragment, ACTIVITY_NAME).commitNowAllowingStateLoss();
        }
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    public RequestManger(FragmentActivity fragmentActivity) {
        requestManagerContext = fragmentActivity;

        androidx.fragment.app.FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (fragment == null) {
            fragment = new FragmentActivityFragmentManager(requestTargetEngine);
            supportFragmentManager.beginTransaction().add(fragment, FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss();
        }
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }


    public RequestTargetEngine load(String path) {
        mHandler.removeMessages(NEXT_HANDLER_MSG);
        //把值传递给资源加载引擎
        requestTargetEngine.loadValueInitAction(path, requestManagerContext);
        return requestTargetEngine;

    }


    private static Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            return false;
        }
    });

}
