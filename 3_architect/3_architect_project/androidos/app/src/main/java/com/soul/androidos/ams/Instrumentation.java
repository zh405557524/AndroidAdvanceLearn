package com.soul.androidos.ams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.soul.androidos.os.Context;

/**
 * Description: 系统与应用交互的检测类
 * Author: 祝明
 * CreateDate: 2021/6/8 11:09
 * UpdateUser:
 * UpdateDate: 2021/6/8 11:09
 * UpdateRemark:
 */
public class Instrumentation {


    /**
     * 2、通知AMS启动Activity
     *
     * @return
     */
    public android.app.Instrumentation.ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                                                        Intent intent, int requestCode, Bundle options) {

        return null;
    }


}
