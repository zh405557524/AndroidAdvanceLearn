package com.soul.animator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/29 下午2:31
 * UpdateUser:
 * UpdateDate: 2019/4/29 下午2:31
 * UpdateRemark:
 */
public class ParallaxFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final int layoutId = args.getInt("layoutId");

        final ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater, getActivity(), this);

        return parallaxLayoutInflater.inflate(layoutId, null);
    }
}
