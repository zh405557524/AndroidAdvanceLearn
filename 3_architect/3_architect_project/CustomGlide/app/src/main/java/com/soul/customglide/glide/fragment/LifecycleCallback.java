package com.soul.customglide.glide.fragment;

/**
 * @Description：生命周期回调
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 15:14
 */
public interface LifecycleCallback {

    /**
     * 生命周期初始化
     */
    void glideInitAction();

    /**
     * 生命周期 停止了
     */
    void glideStopAction();

    /**
     * 生命周期 释放 操作了
     */
    void glideRecycleAction();

}
