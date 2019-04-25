package com.soul.event.listener;

import com.soul.event.MotionEvent;
import com.soul.event.View;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/25 下午4:08
 * UpdateUser:
 * UpdateDate: 2019/4/25 下午4:08
 * UpdateRemark:
 */
public interface OnTouchListener {

    boolean onTouch(View view, MotionEvent motionEvent);
}
