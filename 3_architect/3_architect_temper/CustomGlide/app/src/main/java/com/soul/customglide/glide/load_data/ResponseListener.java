package com.soul.customglide.glide.load_data;

import com.soul.customglide.glide.resource.Value;

/**
 * @Description：加载外部资源 成功与失败的回调
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 23:33
 */
public interface ResponseListener {
    /***
     * 成功
     * @param value
     */
    void responseSuccess(Value value);

    /**
     * 失败
     *
     * @param e
     */
    void responseException(Exception e);
}
