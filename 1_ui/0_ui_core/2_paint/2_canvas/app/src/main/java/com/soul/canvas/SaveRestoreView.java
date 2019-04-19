package com.soul.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:状态保存和恢复
 * Author: 祝明
 * CreateDate: 2019/4/19 下午2:23
 * UpdateUser:
 * UpdateDate: 2019/4/19 下午2:23
 * UpdateRemark:
 */
public class SaveRestoreView extends View {

    private Paint mPaint;

    public SaveRestoreView(Context context) {
        this(context, null);
    }

    public SaveRestoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveRestoreView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        canvas.drawRect(100, 100, 350, 350, mPaint);
        canvas.translate(50, 50);

        mPaint.setColor(Color.GREEN);

        canvas.drawRect(0, 0, 250, 250, mPaint);

    }


}
