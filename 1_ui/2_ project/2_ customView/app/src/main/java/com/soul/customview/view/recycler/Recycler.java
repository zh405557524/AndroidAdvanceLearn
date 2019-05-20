package com.soul.customview.view.recycler;

import android.view.View;

import java.util.Stack;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-17 16:38
 * UpdateUser:
 * UpdateDate: 2019-05-17 16:38
 * UpdateRemark:
 */
public class Recycler {


    Stack<View>[] views;

    public Recycler(int typeNumber) {
        views = new Stack[typeNumber];
        for (int i = 0; i < typeNumber; i++) {
            views[i] = new Stack<>();
        }
    }

    public void put(View view, int type) {
        views[type].push(view);
    }

    public View get(int type) {
        try {
            return views[type].pop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
