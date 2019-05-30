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

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 圆的路径
     */
    private Path mPath;
    /**
     * 路径测量
     */
    private PathMeasure mPathMeasure;
    /**
     * 绘制的路径
     */
    private Path mDstPath;
    /**
     * 路径百分比
     */
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
        //2 创建圆
        mPath.addCircle(150, 150, 150, Path.Direction.CW);
        //3 测量圆
        mPathMeasure = new PathMeasure(mPath, true);
        mDstPath = new Path();
        //4 动画
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
        //获取长度
        final float length = mPathMeasure.getLength() * mAnimatedValue;
        //计算起点
        float start = (float) (length - (0.5 - Math.abs(mAnimatedValue - 0.5f)) * length);
        //截取路径片段
        mPathMeasure.getSegment(start, length, mDstPath, true);
        canvas.drawPath(mDstPath, mPaint);
    }
}
