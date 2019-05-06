package com.soul.adaptive.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/5/6 上午10:44
 * UpdateUser:
 * UpdateDate: 2019/5/6 上午10:44
 * UpdateRemark:
 */
public class UIRelativeLayout extends RelativeLayout {


    private boolean flag = true;

    public UIRelativeLayout(Context context) {
        this(context, null);
    }

    public UIRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("Tag", "UIRelativeLayout_onMeasure");
        if (flag) {
            flag = false;
            final float scaleX = UIUtils.getInstance(getContext()).getHorizontalScaleValue();
            final float scaleY = UIUtils.getInstance(getContext()).getVerticalScaleValue();
            final int childCount = this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = this.getChildAt(i);
                final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                layoutParams.width = (int) (layoutParams.width * scaleX);
                layoutParams.height = (int) (layoutParams.height * scaleY);
                layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
                layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
                layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
                layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}




























