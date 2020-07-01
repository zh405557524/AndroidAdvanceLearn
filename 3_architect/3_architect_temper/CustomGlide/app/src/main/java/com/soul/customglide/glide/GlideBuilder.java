package com.soul.customglide.glide;

import com.soul.customglide.glide.request.RequestMangerRetriver;

/**
 * @Description：TODO
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 15:00
 */
public class GlideBuilder {


    public Glide build() {
        return new Glide(new RequestMangerRetriver());
    }
}
