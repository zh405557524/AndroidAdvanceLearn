package com.soul.androidos.os.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 服务存储
 * Author: 祝明
 * CreateDate: 2021/5/12 10:33
 * UpdateUser:
 * UpdateDate: 2021/5/12 10:33
 * UpdateRemark:
 */
public abstract class ServiceManagerNative extends Binder implements IServiceManager {


    public ServiceManagerNative() {
        attachInterface(this, descriptor);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    public static IServiceManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }

        //本地进程查找
        IInterface iInterface = obj.queryLocalInterface(descriptor);
        if (iInterface != null && iInterface instanceof IServiceManager) {
            return (IServiceManager) iInterface;
        }
        return new ServiceManagerProxy(obj);
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        try {
            switch (code) {
                case IServiceManager.ADD_SERVICE_TRANSACTION://添加服务
                {
                    data.enforceInterface(IServiceManager.descriptor); //data 获取数据
                    String name = data.readString();  //从data 中 获取参数数据
                    IBinder iBinder = data.readStrongBinder();
                    boolean allowIsolated = data.readInt() != 0;
                    addService(name, iBinder, allowIsolated);
                    return true;
                }

                case IServiceManager.GET_SERVICE_TRANSACTION://获取服务
                {
                    data.enforceInterface(IServiceManager.descriptor);
                    String name = data.readString();
                    IBinder service = getService(name);
                    reply.writeStrongBinder(service);
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

class ServiceManagerProxy implements IServiceManager {

    private IBinder mRemote;

    public ServiceManagerProxy(IBinder remote) {
        mRemote = remote;
    }

    @Override
    public IBinder getService(String name) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken(IServiceManager.descriptor);
        data.writeString(name);//写入数据

        mRemote.transact(GET_SERVICE_TRANSACTION, data, reply, 0);
        IBinder iBinder = reply.readStrongBinder();
        reply.recycle();
        data.recycle();
        return iBinder;
    }

    @Override
    public void addService(String name, IBinder service, boolean allowIsolated) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken(IServiceManager.descriptor);
        data.writeString(name);
        data.writeStrongBinder(service);
        data.writeInt(allowIsolated ? 1 : 0);
        mRemote.transact(ADD_SERVICE_TRANSACTION, data, reply, 0);
        reply.recycle();
        data.recycle();
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}