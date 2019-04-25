package com.soul.event;

import com.soul.event.listener.OnClickListener;
import com.soul.event.listener.OnTouchListener;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/25 下午5:23
 * UpdateUser:
 * UpdateDate: 2019/4/25 下午5:23
 * UpdateRemark:
 */
public class Activity {


    public static void main(String[] arg) {

        final ViewGroup viewGroup = new ViewGroup(0, 0, 1080, 1920);
        viewGroup.setName("顶级容器");
        final ViewGroup viewGroup1 = new ViewGroup(0, 0, 500, 500);
        viewGroup.setName("第二季容器");


        final View view = new View(0, 0, 200, 200);
        viewGroup1.addView(view);

        viewGroup.addView(viewGroup1);

        viewGroup.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.print("顶级的OnTouch事件--");
                return false;
            }
        });

        viewGroup1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.print("第二季容器OnTouch事件--");
                return false;
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onCLick(View view) {
                System.out.print("View的onCLick事件--");
            }
        });
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.print("View的OnTouch事件--X:" + motionEvent.getX() + "---Y:" + motionEvent.getY());
                return true;
            }
        });


        final MotionEvent motionEvent = new MotionEvent(100, 100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);

        //顶级容器分发
        viewGroup.dispatchTouchEvent(motionEvent);
        MotionEvent motionEvent1 = null;


        for (int i = 0; i < 50; i++) {
            motionEvent1 = new MotionEvent(i + 20, i + 50);
            motionEvent1.setActionMasked(MotionEvent.ACTION_MOVE);
            viewGroup.dispatchTouchEvent(motionEvent1);
        }


    }

}
