package com.soul.androidos.os.service;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/**
 * Description: 服务管理对象
 * Author: 祝明
 * CreateDate: 2021/5/13 9:57
 * UpdateUser:
 * UpdateDate: 2021/5/13 9:57
 * UpdateRemark:
 */
public interface IServiceManager extends IInterface {

    String descriptor = "android.os.IServiceManager";

    /**
     * 获取服务binder对象
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    IBinder getService(String name) throws RemoteException;

    /**
     * 添加服务binder对象
     *
     * @param name
     * @param service
     * @param allowIsolated
     * @throws RemoteException
     */
    void addService(String name, IBinder service, boolean allowIsolated) throws RemoteException;


    int GET_SERVICE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION;
    int CHECK_SERVICE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION+1;
    int ADD_SERVICE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION+2;
    int LIST_SERVICES_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION+3;
    int CHECK_SERVICES_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION+4;
    int SET_PERMISSION_CONTROLLER_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION+5;
}
