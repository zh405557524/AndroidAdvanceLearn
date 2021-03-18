package com.soul.customglide.glide.cache;

import com.soul.customglide.glide.resource.Value;

/**
 * @Description：内存缓存中，元素被移除的接口回调
 * @Author：祝明
 * @ProjectName:CustomGlide
 * @CreateData：2020/6/5 2:32
 */
public interface MemoryCacheCallback {

    void entryRemoveMemoryCache(String key, Value oldValue);
}
