package com.soul.androidos.os.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 仿写 ServiceManager  服务的存储与获取
 * Author: 祝明
 * CreateDate: 2021/5/12 9:49
 * UpdateUser:
 * UpdateDate: 2021/5/12 9:49
 * UpdateRemark:
 */
public class ServiceManager {

    private static IServiceManager sServiceManger;

    private static HashMap<String, IBinder> sCache = new HashMap<>();

    public ServiceManager() {

    }


    /**
     * ------------------------------与源码无关代码 start--------------------------------
     */
    private static IBinder sBinder;

    public void init(Context context, final ServiceConnection connection) {
        //模拟binder的
        Intent intent = new Intent(context, ServerManagerService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                sBinder = service;
                connection.onServiceConnected(name, service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                connection.onServiceDisconnected(name);
            }
        }, Service.BIND_AUTO_CREATE);
    }

    /**
     * ------------------------------与源码无关代码 end--------------------------------
     */

    /**
     * 添加binder 服务
     *
     * @param name
     * @param service
     */
    public static void addService(String name, IBinder service) {
        try {
            getIServiceManager().addService(name, service, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取到IBinder对象
     *
     * @param name
     * @return
     */
    public static IBinder getService(String name) {
        try {
            IBinder iBinder = sCache.get(name);
            if (iBinder != null) {
                return iBinder;
            } else {
                return getIServiceManager().getService(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 初始化服务对象合集
     *
     * @param cache
     */
    public static void initServiceCache(Map<String, IBinder> cache) {
        if (sCache.size() != 0) {
            throw new IllegalStateException("setServiceCache may only be called once");
        }
        sCache.putAll(cache);
    }


    /**
     * 获取服务管理类
     *
     * @return
     */
    private static IServiceManager getIServiceManager() {
        if (sServiceManger != null) {
            return sServiceManger;
        }
        IBinder iBinder = getBinder();
        sServiceManger = ServiceManagerNative.asInterface(iBinder);
        return sServiceManger;
    }

    private static IBinder getBinder() {
        //  源码为      BinderInternal.getContextObject();
        return sBinder;
    }


}
