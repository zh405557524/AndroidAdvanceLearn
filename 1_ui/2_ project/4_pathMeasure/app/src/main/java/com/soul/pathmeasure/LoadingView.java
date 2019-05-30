package com.soul.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-30 10:44
 * UpdateUser:
 * UpdateDate: 2019-05-30 10:44
 * UpdateRemark:
 */
public class LoadingView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Path mDstPath;
    private float mAnimatedValue;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //1 创建画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(4);
        mPath = new Path();
        mPath.addCircle(150, 150, 150, Path.Direction.CW);
        mPathMeasure = new PathMeasure(mPath, true);
        mDstPath = new Path();
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDstPath.reset();
        final float length = mPathMeasure.getLength() * mAnimatedValue;
        float start = (float) (length - (0.5 - Math.abs(mAnimatedValue - 0.5f)) * length);
        mPathMeasure.getSegment(start, length, mDstPath, true);
        canvas.drawPath(mDstPath, mPaint);
    }
}
