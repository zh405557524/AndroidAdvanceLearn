package com.soul.customglide.glide.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

/**
 * @Description：TODO
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 15:26
 */
public class FragmentActivityFragmentManager extends Fragment {

    private LifecycleCallback mLifecycleCallback;


    public FragmentActivityFragmentManager() {
    }

    @SuppressLint("ValidFragment")
    public FragmentActivityFragmentManager(LifecycleCallback lifecycleCallback) {
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
