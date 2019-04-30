package com.soul.animator;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/29 下午2:22
 * UpdateUser:
 * UpdateDate: 2019/4/29 下午2:22
 * UpdateRemark:
 */
public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallaxFragment> mFragments;
    private ParallaxPagerAdapter mAdapter;


    public ParallaxContainer(Context context) {
        this(context, null);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(int... childIds) {
        mFragments = new ArrayList<>();

        for (int i = 0; i < childIds.length; i++) {
            final ParallaxFragment parallaxFragment = new ParallaxFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt("layoutId", childIds[i]);
            parallaxFragment.setArguments(bundle);
            mFragments.add(parallaxFragment);
        }

        final ViewPager viewPager = new ViewPager(getContext());
        viewPager.setId(R.id.parallax_pager);

        final MainActivity activity = (MainActivity) getContext();
        mAdapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager.addOnPageChangeListener(this);
        addView(viewPager);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //动画操作
        final int containerWidth = getWidth();
        ParallaxFragment inFragment = null;
        try {
            if (position != 0) {
                inFragment = mFragments.get(position - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ParallaxFragment outFragment = null;
        try {
            outFragment = mFragments.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (inFragment != null) {
            final List<View> inViews = inFragment.getParallaxViews();
            //动画
            if (inViews != null) {
                for (View view : inViews) {
                    final ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    ViewHelper.setTranslationX(view, (containerWidth - positionOffsetPixels) * tag.xIn);
                    ViewHelper.setTranslationY(view, (containerWidth - positionOffsetPixels) * tag.yIn);
                }
            }
        }

        if (outFragment != null) {
            final List<View> outViews = outFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    final ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    ViewHelper.setTranslationX(view, (0 - positionOffsetPixels) * tag.xOut);
                    ViewHelper.setTranslationY(view, (0 - positionOffsetPixels) * tag.yOut);
                }
            }
        }

    }

    private ImageView iv_man;

    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    @Override
    public void onPageSelected(int position) {
        if (position == mAdapter.getCount() - 1) {
            iv_man.setVisibility(INVISIBLE);
        } else {
            iv_man.setVisibility(VISIBLE);
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        final AnimationDrawable animation = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;
            default:
                break;
        }

    }
}
