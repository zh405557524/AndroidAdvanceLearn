package com.soul.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-29 10:25
 * UpdateUser:
 * UpdateDate: 2019-05-29 10:25
 * UpdateRemark:
 */
public class RippleCircleView extends View {

    private RippleAnimationView mRippleAnimationView;


    public RippleCircleView(RippleAnimationView context) {
        this(context.getContext(), null);
        mRippleAnimationView = context;

    }

    public RippleCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getWidth() / 2;
        canvas.drawCircle(width, width, width - mRippleAnimationView.strokWidth, mRippleAnimationView.mPaint);
    }
}
