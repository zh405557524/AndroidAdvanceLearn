package com.soul.animator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

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
        final ParallaxPagerAdapter adapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(adapter);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(viewPager);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
