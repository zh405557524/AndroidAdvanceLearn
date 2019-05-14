package com.soul.materialdesign.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-14 15:05
 * UpdateUser:
 * UpdateDate: 2019-05-14 15:05
 * UpdateRemark:
 */
public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {


    private FastOutLinearInInterpolator mFastOutLinearInInterpolator = new FastOutLinearInInterpolator();
    private LinearOutSlowInInterpolator mLinearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    private boolean isRunning;

    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL; //垂直滚动
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        if (dyConsumed > 0 && !isRunning && child.getVisibility() == View.VISIBLE) {//向下滑动
            //隐藏
            scaleHide(child);
        } else if (dyConsumed < 0 && !isRunning && child.getVisibility() == View.INVISIBLE) {//向上滑动
            //显示
            scaleShow(child);
        }
    }

    private void scaleShow(V child) {
        child.setVisibility(View.VISIBLE);
        ViewCompat.animate(child)
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setInterpolator(mLinearOutSlowInInterpolator)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        isRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        isRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        super.onAnimationCancel(view);
                        isRunning = false;
                    }
                }).start();
    }

    private void scaleHide(final V child) {
        ViewCompat.animate(child)
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setInterpolator(mFastOutLinearInInterpolator)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        isRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        isRunning = false;
                        child.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        super.onAnimationCancel(view);
                        isRunning = false;
                    }
                }).start();
    }
}
