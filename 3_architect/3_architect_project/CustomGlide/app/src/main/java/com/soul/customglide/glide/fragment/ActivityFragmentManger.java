package com.soul.customglide.glide.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;


/**
 * @Description：TODO
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 15:13
 */
public class ActivityFragmentManger extends Fragment {

    private LifecycleCallback mLifecycleCallback;


    public ActivityFragmentManger() {
    }

    @SuppressLint("ValidFragment")
    public ActivityFragmentManger(LifecycleCallback lifecycleCallback) {
        this.mLifecycleCallback = lifecycleCallback;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mLifecycleCallback != null) {
            mLifecycleCallback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLifecycleCallback != null) {
            mLifecycleCallback.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLifecycleCallback != null) {
            mLifecycleCallback.glideRecycleAction();
        }
    }
}
