package com.soul.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/17 上午9:30
 * UpdateUser:
 * UpdateDate: 2019/4/17 上午9:30
 * UpdateRemark:
 */
public class ColorFilterView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private ColorMatrixColorFilter mColorMatrixColorFilter;

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //滤镜 红色去除
        //        LightingColorFilter lighting = new LightingColorFilter(0x00ffff, 0x000000);
        //原始效果
        //        LightingColorFilter lighting = new LightingColorFilter(0xffffff, 0x000000);
        //绿色更亮
        //        LightingColorFilter lighting = new LightingColorFilter(0xffffff, 0x003000);
        //        mPaint.setColorFilter(lighting);
        //        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        //        PorterDuffColorFilter porterDuffColorFilter = new
        //                PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
        //        mPaint.setColorFilter(porterDuffColorFilter);
        //        canvas.drawBitmap(mBitmap, 100, 0, mPaint);

        float[] colorMatrix = {
                2, 0, 0, 0, 0,//red
                0, 1, 0, 0, 0,//green
                0, 0, 1, 0, 0,//blue
                0, 0, 0, 1, 0 //alpha
        };
        final ColorMatrix cm = new ColorMatrix();
        //亮度调节
        cm.setScale(1, 1, 1, 1);

        //饱和度调节 0 无色彩 1 默认效果 >1 饱和度加强
        //        cm.setSaturation(2);

        // 色调
        // axi  红 绿  蓝 ，
        // degress 角度
        cm.setRotate(2, 45);
        mColorMatrixColorFilter = new ColorMatrixColorFilter(cm);
        mPaint.setColorFilter(mColorMatrixColorFilter);
        canvas.drawBitmap(mBitmap, 100, 0, mPaint);

    }

    // 胶片2
    public static final float colormatrix_jiao_pian[] = {
            0.71f, 0.2f, 0.0f, 0.0f, 60.0f,
            0.0f, 0.94f, 0.0f, 0.0f, 60.0f,
            0.0f, 0.0f, 0.62f, 0.0f, 60.0f,
            0, 0, 0, 1.0f, 0};


}
