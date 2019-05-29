package com.soul.pathmeasure;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-29 10:26
 * UpdateUser:
 * UpdateDate: 2019-05-29 10:26
 * UpdateRemark:
 */
public class RippleAnimationView extends RelativeLayout {


    /**
     * 颜色
     */
    int rippleColor;
    /**
     * 半径
     */
    int radius;
    /**
     * 宽度
     */
    public int strokWidth;
    /**
     * 画笔
     */
    public Paint mPaint;
    private AnimatorSet mAnimatorSet;

    public RippleAnimationView(Context context) {
        this(context, null);
    }

    public RippleAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        rippleColor = array.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(context, R.color.rippleColor));
        radius = array.getInt(R.styleable.RippleAnimationView_radius, 0);
        strokWidth = array.getInt(R.styleable.RippleAnimationView_strokWidth, 0);
        final int rippleType = array.getInt(R.styleable.RippleAnimationView_ripple_anim_type, 0);

        //1 创建画笔，设置画笔样式
        mPaint = new Paint();
        mPaint.setColor(rippleColor);
        if (rippleType == 0) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        //2 设置动画时间
        long rippleDuration = 5000;
        final long singleDelay = rippleDuration / 4;
        float maxScale = 2;
        //3 创建view 并添加动画
        final RippleCircleView rippleCircleView = new RippleCircleView(this);
        final LayoutParams layoutParams = new LayoutParams(UIUtils.getInstance().getWidth(radius + strokWidth),
                UIUtils.getInstance().getWidth(radius + strokWidth));
        layoutParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(rippleCircleView, layoutParams);
        ArrayList<Animator> animatorArrayList = new ArrayList<>();
        //透明度
        final ObjectAnimator alpha = ObjectAnimator.ofFloat(rippleCircleView, "Alpha", 1.0f, 0.f);
        alpha.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
        alpha.setRepeatMode(ObjectAnimator.RESTART);
        //        alpha.setStartDelay(singleDelay);
        alpha.setDuration(rippleDuration);
        //        animatorArrayList.add(alpha);


        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(rippleCircleView, "scaleX", 1.0f, maxScale);
        alpha.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
        alpha.setRepeatMode(ObjectAnimator.RESTART);
        //        alpha.setStartDelay(singleDelay);
        alpha.setDuration(rippleDuration);
        animatorArrayList.add(scaleX);


        final ObjectAnimator scaleY = ObjectAnimator.ofFloat(rippleCircleView, "scaleY", 1.0f, maxScale);
        alpha.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
        alpha.setRepeatMode(ObjectAnimator.RESTART);
        //        alpha.setStartDelay(singleDelay);
        alpha.setDuration(rippleDuration);
        animatorArrayList.add(scaleY);


        //缩放 x轴
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.playTogether(animatorArrayList);
        //4 启动动画
        //5 停止动画
        array.recycle();
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("Tag", "animation:" + animation.getDuration());
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    public void start() {
        mAnimatorSet.start();

    }
}
