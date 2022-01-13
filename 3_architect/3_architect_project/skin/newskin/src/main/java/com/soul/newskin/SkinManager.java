package com.soul.newskin;

import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.soul.newskin.model.SkinCache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 皮肤管理器
 * Author: 祝明
 * CreateDate: 2021/12/31 10:25
 * UpdateUser:
 * UpdateDate: 2021/12/31 10:25
 * UpdateRemark:
 */
public class SkinManager {


    private Application application;

    private static SkinManager sInstance;

    private Map<String, SkinCache> cacheSkin;

    private final Resources mResources;

    private Resources mSkinResources;

    private String mSkinPackageName;

    private String skinPackageName;

    private static final String ADD_ASSET_PATH = "addAssetPath"; // 方法名

    public SkinManager(Application application) {
        this.application = application;
        mResources = application.getResources();
        cacheSkin = new HashMap<>();
    }

    public static void skinApplicationInit(Application application) {
        sInstance = new SkinManager(application);
    }

    public static SkinManager getInstance() {
        return sInstance;
    }

    private boolean isDefaultSkin;

    /**
     * 加载皮肤包资源
     *
     * @param skinPath 皮肤包路径，为空则加载app内置资源
     */
    public void loaderSkinResources(String skinPath) {
        if (TextUtils.isEmpty(skinPath)) {
            isDefaultSkin = true;
            return;
        }
        if (cacheSkin.containsKey(skinPath)) {
            isDefaultSkin = false;
            SkinCache skinCache = cacheSkin.get(skinPath);
            if (skinCache != null) {
                mSkinResources = skinCache.getSkinResources();
                mSkinPackageName = skinCache.getSkinPackageName();
            }
        }

        try {
            // 创建资源管理器（此处不能用：application.getAssets()）
            AssetManager assetManager = AssetManager.class.newInstance();
            // 由于AssetManager中的addAssetPath和setApkAssets方法都被@hide，目前只能通过反射去执行方法
            Method addAssetPath = assetManager.getClass().getDeclaredMethod(ADD_ASSET_PATH, String.class);
            // 设置私有方法可访问
            addAssetPath.setAccessible(true);
            // 执行addAssetPath方法
            addAssetPath.invoke(assetManager, skinPath);
            //==============================================================================
            // 如果还是担心@hide限制，可以反射addAssetPathInternal()方法，参考源码366行 + 387行
            //==============================================================================

            // 创建加载外部的皮肤包(net163.skin)文件Resources（注：依然是本应用加载）
            mSkinResources = new Resources(assetManager,
                    mResources.getDisplayMetrics(), mResources.getConfiguration());

            // 根据apk文件路径（皮肤包也是apk文件），获取该应用的包名。兼容5.0 - 9.0（亲测）
            skinPackageName = application.getPackageManager()
                    .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

            // 无法获取皮肤包应用的包名，则加载app内置资源
            isDefaultSkin = TextUtils.isEmpty(skinPackageName);
            if (!isDefaultSkin) {
                cacheSkin.put(skinPath, new SkinCache(mSkinResources, skinPackageName));
            }

            Log.e("skinPackageName >>> ", skinPackageName);

        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常，预判：通过skinPath获取skinPacakageName失败！
            isDefaultSkin = true;
        }

    }


}
