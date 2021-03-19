package com.soul.aop.invocationhandler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.soul.aop.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 动态代理
 */
public class InvocationHandlerActivity extends AppCompatActivity implements View.OnClickListener, DBOperation {

    public static String TAG = InvocationHandlerActivity.class.getSimpleName();
    private DBOperation mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invocation_handler);
        findViewById(R.id.bt_click).setOnClickListener(this);
        DBHandler h = new DBHandler(this);
        mDb = (DBOperation) Proxy.newProxyInstance(DBOperation.class.getClassLoader(), new Class[]{DBOperation.class}, h);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_click:
                mDb.delete();
                break;
        }
    }

    @Override
    public void insert() {
        Log.e(TAG, "新增数据");
    }

    @Override
    public void delete() {
        Log.e(TAG, "删除数据");
    }

    @Override
    public void update() {
        Log.e(TAG, "修改数据");
    }

    @Override
    public void save() {
        Log.e(TAG, "保存数据");
    }


    private class DBHandler implements InvocationHandler {

        private DBOperation db;

        private DBHandler(DBOperation db) {
            this.db = db;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (db != null) {
                Log.i(TAG, "操作数据库之前，开始备份");
                save();
                Log.i(TAG, "数据备份完成，等待操作");
                return method.invoke(db, args);
            }
            return null;
        }
    }
}

