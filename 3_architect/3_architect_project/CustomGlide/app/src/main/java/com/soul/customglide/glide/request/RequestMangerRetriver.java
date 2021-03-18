package com.soul.customglide.glide.request;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

/**
 * @Description：TODO
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 14:57
 */
public class RequestMangerRetriver {

    public RequestManger get(FragmentActivity activity) {

        return new RequestManger(activity);
    }


    public RequestManger get(Activity activity) {
        return new RequestManger(activity);
    }

    public RequestManger get(Context context) {
        return new RequestManger(context);
    }


}
