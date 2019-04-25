package com.soul.event;

import com.soul.event.listener.OnClickListener;
import com.soul.event.listener.OnTouchListener;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/25 下午4:07
 * UpdateUser:
 * UpdateDate: 2019/4/25 下午4:07
 * UpdateRemark:
 */
public class View {

    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;

    public View() {

    }

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }


    public boolean isContainer(int x, int y) {
        if (x >= left && x < right && y >= top && y < bottom) {
            return true;
        }
        return false;
    }

    //接受分发的代码
    public boolean dispatchTouchEvent(MotionEvent event) {
        //消费
        boolean result = false;
        if (mOnTouchListener != null && mOnTouchListener.onTouch(this, event)) {
            result = true;
        }

        if (!result && onTouchEvent(event)) {
            result = true;
        }

        return result;
    }


    private boolean onTouchEvent(MotionEvent event) {
        if (mOnClickListener != null) {
            mOnClickListener.onCLick(this);
            return true;
        }
        return false;
    }

}
