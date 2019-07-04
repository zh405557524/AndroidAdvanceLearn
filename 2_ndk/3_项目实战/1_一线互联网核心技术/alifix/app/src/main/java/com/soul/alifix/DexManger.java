package com.soul.alifix;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-01 17:47
 * UpdateUser:
 * UpdateDate: 2019-07-01 17:47
 * UpdateRemark:
 */
public class DexManger {

    private Context mContext;


    public DexManger(Context context) {
        this.mContext = context;
    }

    public void load(File file) {
        try {
            final DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                    new File(mContext.getCacheDir(), "opt").getAbsolutePath(), Context.MODE_PRIVATE);
            final Enumeration<String> entry = dexFile.entries();
            while (entry.hasMoreElements()) {
                final String clazzName = entry.nextElement();
                final Class realClazz = dexFile.loadClass(clazzName, mContext.getClassLoader());
                fixClazz(realClazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fixClazz(Class realClazz) {
        final Method[] methods = realClazz.getMethods();
        for (Method rightMethod : methods) {
            final Replace replace = rightMethod.getAnnotation(Replace.class);
            if (replace == null) {
                continue;
            }
            String clazzName = replace.clazz();
            String methodName = replace.method();
            try {
                final Class wrongClazz = Class.forName(clazzName);
                final Method wrongMethod = wrongClazz.getDeclaredMethod(methodName, rightMethod.getParameterTypes());
                replace(wrongMethod, rightMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public native static void replace(Method wrongMethod, Method rightMethod);


}
