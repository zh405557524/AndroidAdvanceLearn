package com.soul.screen;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/5/6 下午6:57
 * UpdateUser:
 * UpdateDate: 2019/5/6 下午6:57
 * UpdateRemark:
 */
public class PercentRelativeLayout extends RelativeLayout {
    public PercentRelativeLayout(Context context) {
        this(context, null);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1 获取父类尺寸
        //2 子类尺寸 = 父类尺寸*百分比
        final int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
        //获取所有子类
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);
            final ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) lp;

                float widthPercent = layoutParams.widthPercent;
                float heightPercent = layoutParams.heightPercent;
                float marginTopPercent = layoutParams.marginTopPercent;
                float marginBottomPercent = layoutParams.marginBottomPercent;
                float marginLeftPercent = layoutParams.marginLeftPercent;
                float marginRightPercent = layoutParams.marginRightPercent;
                if (widthPercent > 0) {
                    lp.width = (int) (widthMeasure * widthPercent);
                }
                if (heightPercent > 0) {
                    lp.height = (int) (heightMeasure * heightPercent);
                }
                if (marginTopPercent > 0) {
                    ((LayoutParams) lp).topMargin = (int) (heightMeasure * marginTopPercent);
                }
                if (marginBottomPercent > 0) {
                    ((LayoutParams) lp).bottomMargin = (int) (heightMeasure * marginBottomPercent);
                }
                if (marginLeftPercent > 0) {
                    ((LayoutParams) lp).leftMargin = (int) (widthMeasure * marginLeftPercent);
                }
                if (marginRightPercent > 0) {
                    ((LayoutParams) lp).rightMargin = (int) (widthMeasure * marginRightPercent);
                }
                view.setLayoutParams(lp);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class LayoutParams extends RelativeLayout.LayoutParams {

        private float widthPercent;//宽
        private float heightPercent;//高
        private float marginTopPercent;//顶部间隙
        private float marginBottomPercent;//底部间隙
        private float marginLeftPercent;//左边间隙
        private float marginRightPercent;//右边间隙


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.percent);
            widthPercent = a.getFloat(R.styleable.percent_widthPercent, 0);
            heightPercent = a.getFloat(R.styleable.percent_heightPercent, 0);
            marginTopPercent = a.getFloat(R.styleable.percent_marginTopPercent, 0);
            marginBottomPercent = a.getFloat(R.styleable.percent_marginBottomPercent, 0);
            marginLeftPercent = a.getFloat(R.styleable.percent_marginLeftPercent, 0);
            marginRightPercent = a.getFloat(R.styleable.percent_marginRightPercent, 0);

        }

    }


}
