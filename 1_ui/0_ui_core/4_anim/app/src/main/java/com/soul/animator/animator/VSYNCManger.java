package com.soul.animator.animator;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/28 下午3:18
 * UpdateUser:
 * UpdateDate: 2019/4/28 下午3:18
 * UpdateRemark:
 */
public class VSYNCManger {


    private static final VSYNCManger ourInstance = new VSYNCManger();

    public static VSYNCManger getInstance() {
        return ourInstance;
    }

    private VSYNCManger() {

        new Thread(mRunnable).start();
    }

    private List<AnimationFrameCallback> list = new ArrayList<>();


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(16);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (AnimationFrameCallback animationFrameCallback : list) {
                    animationFrameCallback.doAnimationFrame(System.currentTimeMillis());

                }
            }
        }
    };

    public void add(AnimationFrameCallback animationFrameCallback) {
        list.add(animationFrameCallback);
    }

    interface AnimationFrameCallback {

        boolean doAnimationFrame(long currentTime);

    }


}
