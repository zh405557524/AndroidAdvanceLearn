package com.soul.oldskin;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Description: 访问 本地存储的皮肤包资源 或者当前app运行的apk 包资源
 * Author: 祝明
 * CreateDate: 2021/11/29 11:00
 * UpdateUser:
 * UpdateDate: 2021/11/29 11:00
 * UpdateRemark:
 */
public class SkinResources {

    public static final String TAG = SkinResources.class.getSimpleName();

    private final Resources mAppResources;

    private static SkinResources instance;

    private boolean isDefaultSkin = true; // 默认是 默认的皮肤(最原始的)
    private Resources mSkinResources;
    private String mSkinPkgName;
    private boolean mIsDefaultSkin;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public static void applicationInit(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }


    /**
     * 此方法是给 点击换肤时使用的，目的就是创建出mSkinResources ---> 访问 本地存储 xxx.skin 皮肤包
     *
     * @param application
     * @param skinPath
     */
    public void setSkinResources(Application application, String skinPath) {
        File file = new File(skinPath);
        if (!file.exists()) {
            Log.e(TAG, "Error skinPath not exist...");
        }

        try {
            AssetManager assetManager = AssetManager.class.newInstance();

            // 由于AssetManager中的addAssetPath和setApkAssets方法都被@hide，目前只能通过反射去执行方法
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("", String.class);
            // 设置私有方法可访问
            addAssetPath.setAccessible(true);
            // 执行addAssetPath方法
            addAssetPath.invoke(assetManager, skinPath);

            // 创建加载外部的皮肤包(xxx.skin)文件Resources
            mSkinResources = new Resources(assetManager, mAppResources.getDisplayMetrics(), mAppResources.getConfiguration());

            // 根据apk文件路径（皮肤包也是apk文件），获取该应用的包名
            mSkinPkgName = application.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

            //是否使用默认皮肤
            mIsDefaultSkin = TextUtils.isEmpty(mSkinPkgName);
        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常就证明 通过skinPath 获取 packageName 失败了
            isDefaultSkin = true;
        }
    }


    /**
     * 获取字体
     *
     * @param resId
     * @return
     */
    public Typeface getTypeface(int resId) {
        getString(resId);

        return null;
    }

    public Object getBackground(int attrValueInt) {

        return null;
    }

    private String getString(int resId) {
        try {
            if (isDefaultSkin) {//如果没有皮肤，那就加载当前app运行的apk资源
                return mAppResources.getString(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

}
