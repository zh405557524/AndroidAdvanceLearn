package com.soul.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:Paint api 集合
 * Author: 祝明
 * CreateDate: 2019/4/9 下午7:08
 * UpdateUser:
 * UpdateDate: 2019/4/9 下午7:08
 * UpdateRemark:
 */
public class GradientLayout extends View {

    /**
     * 画笔
     */
    private Paint mPaint;

    private Shader mShader;
    private Bitmap mBitmap;

    public GradientLayout(Context context) {
        this(context, null);
    }

    public GradientLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);

        mPaint = new Paint();//初始化
        //        mPaint.setColor(Color.RED);//设置颜色
        //        mPaint.setARGB(255, 255, 255, 0);//设置paint对象颜色，范围0~255
        //        mPaint.setAlpha(200);//设置alpha 不透明，范围0~255
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);//描边效果
        //        mPaint.setStrokeWidth(4);//描边宽度
        //        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角效果
        //        mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格
        //        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED));//设置环形渲染器
        //        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));//设置图层混合模式
        //        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000));//设置颜色过滤器
        //        mPaint.setFilterBitmap(true);//设置双线性过滤
        //        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜，传入度数和样式
        //        mPaint.setTextScaleX(2);//设置文本缩放倍数
        //        mPaint.setTextSize(38);//设置字体大小
        //        mPaint.setTextAlign(Paint.Align.LEFT);//对齐方式
        //        mPaint.setUnderlineText(true);//设置下划线
        //
        //        String str = "Android 高级工程师";
        //        final Rect rect = new Rect();
        //        mPaint.getTextBounds(str, 0, str.length(), rect);//测量文本大小，将文本大小信息存放到rect中
        //        mPaint.measureText(str);//获取文本的宽
        //        mPaint.getFontMetrics();//获取字体度量对象

        //1 线性渲染
        mShader = new LinearGradient(0, 0, 250, 250, new int[]{Color.RED, Color.BLUE, Color.GREEN},
                new float[]{0.1f, 0.5f, 1f}, Shader.TileMode.CLAMP);
        //2 环形渲染
        //        mShader = new RadialGradient(250, 250, 250, new int[]{Color.GREEN, Color.YELLOW, Color.RED},
        //                null, Shader.TileMode.CLAMP);

        //3 扫描渲染
        //        mShader = new SweepGradient(250, 250, Color.RED, Color.GREEN);

        //4 位图渲染
        //        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //        mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        //        mShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);

        //5 组合渲染
        BitmapShader bitmapShader = new BitmapShader(mBitmap,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(0, 0, 1000, 16000, new
                int[]{Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.CLAMP);

        mShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1 线性渲染器
        mPaint.setShader(mShader);
        //圆
        canvas.drawCircle(250, 250, 250, mPaint);
        //        canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mPaint);
        //        canvas.drawRect(0, 0, 500, 500, mPaint);
        //矩形
        //        canvas.drawRect(0, 0, 250, 250, mPaint);
    }


}






























