package com.soul.customglide.glide.resource;

import com.soul.customglide.utils.Tool;

/**
 * @描述：图片资源的唯一描述
 * @作者：祝明
 * @项目名:CustomGlide
 * @创建时间：2020/6/3 15:21
 */
public class Key {
    private String key;

    public Key(String key) {
        this.key = Tool.getSHA256StrJava(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
