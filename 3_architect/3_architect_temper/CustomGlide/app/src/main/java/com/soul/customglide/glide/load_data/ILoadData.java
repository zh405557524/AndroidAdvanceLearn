package com.soul.customglide.glide.load_data;

import android.content.Context;

import com.soul.customglide.glide.resource.Value;

/**
 * @Description：加载外部资源
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 23:32
 */
public interface ILoadData {

    /**
     * 加载外部资源
     *
     * @param path
     * @param responseListener
     * @param context
     * @return
     */
    Value loadResource(String path, ResponseListener responseListener, Context context);
}
