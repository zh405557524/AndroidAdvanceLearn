package com.soul.newskin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
                skinPackageName = skinCache.getSkinPackageName();
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

    /**
     * 获取颜色
     *
     * @param resourceId 资源id
     * @return
     */
    public int getColor(int resourceId) {
        int skinResourceIds = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mResources.getColor(resourceId) : mSkinResources.getColor(skinResourceIds);
    }

    /**
     * 获取颜色列表
     *
     * @param resourceId 资源id
     * @return
     */
    @SuppressLint("UseCompatLoadingForColorStateLists")
    public ColorStateList getStateList(int resourceId) {
        int skinResourceIds = getSkinResourceIds(resourceId);

        return isDefaultSkin ? mResources.getColorStateList(resourceId) : mSkinResources.getColorStateList(skinResourceIds);
    }

    public String getString(int resourceId) {
        int skinResourceIds = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mResources.getString(resourceId) : mSkinResources.getString(skinResourceIds);
    }

    /**
     * 获取背景颜色
     *
     * @param resourceId 资源id
     * @return 可能是 color/drawable/mipmap
     */
    public Object getBackgroundOrSrc(int resourceId) {
        String resourceTypeName = mResources.getResourceTypeName(resourceId);
        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);
            case "mipmap":
            case "drawable":
                return getDrawableOrMipMap(resourceId);
        }
        return null;
    }

    /**
     * 获取字体
     *
     * @param resourceId 资源id
     * @return
     */
    public Typeface getTypeface(int resourceId) {
        String skinTypefacePath = getString(resourceId);
        //路径为空，使用系统默认字体
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        return isDefaultSkin ? Typeface.createFromAsset(mResources.getAssets(), skinTypefacePath) :
                Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
    }


    /**
     * 参考:resources.arsc 资源映射表
     * 通过id获取资源Name 和 Type
     *
     * @param resourceId 资源ID值
     * @return 如何没有皮肤包则加载app 内置资源ID,繁殖加载屁护宝指定资源ID
     */
    private int getSkinResourceIds(int resourceId) {
        if (isDefaultSkin) {
            return resourceId;
        }
        String resourceName = mResources.getResourceEntryName(resourceId);
        String resourceType = mResources.getResourceTypeName(resourceId);

        int skinResourceId = mSkinResources.getIdentifier(resourceName, resourceType, skinPackageName);
        isDefaultSkin = skinResourceId == 0;

        return skinResourceId == 0 ? resourceId : skinResourceId;
    }

    /**
     * 获取图片资源
     *
     * @param resourceId 资源ID值
     * @return Drawable
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getDrawableOrMipMap(int resourceId) {
        int skinResourceIds = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mResources.getDrawable(resourceId) : mSkinResources.getDrawable(skinResourceIds);
    }

}
