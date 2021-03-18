package com.soul.customglide.glide;

import android.app.Activity;
import android.content.Context;

import com.soul.customglide.glide.request.RequestManger;
import com.soul.customglide.glide.request.RequestMangerRetriver;

import androidx.fragment.app.FragmentActivity;

/**
 * @Description：TODO
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 14:49
 */
public class Glide {

    RequestMangerRetriver mRetriver;

    public Glide(RequestMangerRetriver retriver) {
        mRetriver = retriver;
    }

    public static RequestManger with(FragmentActivity fragmentActivity) {

        return getRetriver(fragmentActivity).get(fragmentActivity);
    }

    public static RequestManger with(Activity activity) {
        return getRetriver(activity).get(activity);
    }

    public static RequestManger width(Context context) {
        return getRetriver(context).get(context);
    }


    public static RequestMangerRetriver getRetriver(Context context) {
        return Glide.get(context).getRetriver();
    }

    public static Glide get(Context context) {
        return new GlideBuilder().build();
    }


    public RequestMangerRetriver getRetriver() {
        return mRetriver;
    }


}
