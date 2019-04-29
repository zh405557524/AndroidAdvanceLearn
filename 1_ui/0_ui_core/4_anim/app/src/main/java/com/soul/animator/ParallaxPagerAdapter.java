package com.soul.animator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/29 下午2:21
 * UpdateUser:
 * UpdateDate: 2019/4/29 下午2:21
 * UpdateRemark:
 */
public class ParallaxPagerAdapter extends FragmentPagerAdapter {

    private List<ParallaxFragment> fragments;

    public ParallaxPagerAdapter(FragmentManager fm, List<ParallaxFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
