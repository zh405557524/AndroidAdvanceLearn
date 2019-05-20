package com.soul.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.soul.customview.R;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-15 14:25
 * UpdateUser:
 * UpdateDate: 2019-05-15 14:25
 * UpdateRemark:
 */
public class CarView extends View {

    private Paint mPaint;
    private Paint mCarPaint;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private float mCurrentDegree;
    private Matrix mMatrix;

    public CarView(Context context) {
        this(context, null);
    }

    public CarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1 初始化 画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        mCarPaint = new Paint();
        mCarPaint.setAntiAlias(true);
        //2 初始化 图片
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car);
        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final Path path = new Path();
        //4 画 坐标系，画圆
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(-mWidth, 0, mWidth, 0, mPaint);
        canvas.drawLine(0, -mHeight, 0, mHeight, mPaint);
        path.addCircle(0, 0, 200, Path.Direction.CW);
        //5 计算小车的位置
        mCurrentDegree += 0.001f;
        if (mCurrentDegree >= 1) {
            mCurrentDegree = 0;
        }
        final PathMeasure pathMeasure = new PathMeasure(path, false);

        final float distance = pathMeasure.getLength() * mCurrentDegree;
        float[] pos = new float[2];//坐标
        float[] tan = new float[2];//tan值 tan[0] 临边  tan[1]对边
        pathMeasure.getPosTan(distance, pos, tan);
        mMatrix.reset();

        //计算图片角度
        // atan2返回的是弧度值，并不是角度，也就是从-PI到PI的弧度，一个半圆是180度=弧度PI
        // 所以：1度 = (PI/180)弧度，1弧度 = (180/PI)度
        // 计算小车本身要旋转的角度，就需要用弧度值乘以(180/PI)
        float degree = (float) ((Math.atan2(tan[1], tan[0]) * 180.f) / Math.PI);
        Log.i("Tag", "degree:" + degree);
        mMatrix.postRotate(degree, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);//设置旋转中心跟角度
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
        //6 画出小车
        canvas.drawPath(path, mPaint);
        canvas.drawBitmap(mBitmap, mMatrix, mCarPaint);
        invalidate();
    }
}
