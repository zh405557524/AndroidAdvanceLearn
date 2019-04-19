package com.soul.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:变换操作
 * Author: 祝明
 * CreateDate: 2019/4/18 下午5:35
 * UpdateUser:
 * UpdateDate: 2019/4/18 下午5:35
 * UpdateRemark:
 */
public class TransformView extends View {

    private Paint mPaint;

    public TransformView(Context context) {
        this(context, null);
    }

    public TransformView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransformView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1. 平移操作
        //        canvas.drawRect(0, 0, 400, 400, mPaint);
        //        canvas.translate(50, 50);
        //        mPaint.setColor(Color.GRAY);
        //        canvas.drawRect(0, 0, 400, 400, mPaint);
        //        canvas.drawLine(0, 0, 600, 600, mPaint);

        //2. 缩放操作
        //        canvas.drawRect(200, 200, 700, 700, mPaint);
        //        canvas.scale(0.5f, 0.5f);
        //        //先translate(px,py) ,再 scale（sx,sy） 在反向 translate
        //        //        canvas.scale(0.5f, 0.5f, 200, 200);
        //        mPaint.setColor(Color.GRAY);
        //        canvas.drawRect(200, 200, 700, 700, mPaint);

        //3. 旋转操作
        //        canvas.drawRect(0, 0, 300, 300, mPaint);
        //        canvas.rotate(45);
        //        mPaint.setColor(Color.GRAY);
        //        canvas.drawRect(0, 0, 300, 300, mPaint);

        //        canvas.drawRect(200, 200, 350, 350, mPaint);
        //        canvas.rotate(45, 300, 300);
        //        mPaint.setColor(Color.GRAY);
        //        canvas.drawRect(200, 200, 350, 350, mPaint);

        //4. 倾斜操作
        //        canvas.drawRect(0, 0, 200, 200, mPaint);
        //                canvas.skew(1, 0);//在x方向倾斜45度,y轴逆时针旋转45
        //        canvas.skew(0, 1);//在y方向倾斜45度，x轴顺时针旋转45
        //        mPaint.setColor(Color.GRAY);
        //        canvas.drawRect(0, 0, 200, 200, mPaint);


        //5. 切割
        //        canvas.drawRect(100, 100, 350, 350, mPaint);
        //        canvas.clipRect(100, 100, 350, 350);
        //        canvas.drawRect(100, 100, 350, 500, mPaint);
        //        canvas.clipRect(100, 100, 350, 350);//画布被裁减
        //        canvas.drawCircle(50, 50, 50, mPaint);//坐标超出裁减区域，无法绘制
        //        canvas.drawCircle(150, 150, 50, mPaint);//坐标渔区在裁剪范围内，绘制成功

        //反向裁剪
        //        canvas.clipOutRect(100, 100, 350, 350);//画布裁剪外的区域
        //        canvas.drawCircle(50, 50, 50, mPaint);//坐标超出裁减区域，绘制成功
        //        canvas.drawCircle(150, 150, 50, mPaint);////坐标渔区在裁剪范围内，无法绘制

        //6 矩阵
        canvas.drawRect(0, 0, 350, 350, mPaint);
        Matrix matrix = new Matrix();
        //        matrix.setTranslate(50, 50);
        //        matrix.setRotate(45);
        matrix.setScale(0.5f, 0.5f);
        canvas.setMatrix(matrix);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, 350, 350, mPaint);






    }

}
