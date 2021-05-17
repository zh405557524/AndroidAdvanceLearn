package com.soul.androidos.os.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;

public class ServerManagerService extends Service {

    private static HashMap<String, IBinder> sCache = new HashMap<>();

    public ServerManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceManagerStub();
    }


    private class ServiceManagerStub extends ServiceManagerNative {

        @Override
        public IBinder getService(String name) throws RemoteException {
            return sCache.get(name);
        }

        @Override
        public void addService(String name, IBinder service, boolean allowIsolated) throws RemoteException {
            sCache.put(name, service);
        }
    }
}

