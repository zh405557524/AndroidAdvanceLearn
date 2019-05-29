package com.soul.vlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-24 13:42
 * UpdateUser:
 * UpdateDate: 2019-05-24 13:42
 * UpdateRemark:
 */
public class BaseDelegetAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;
    private Context mContext;
    private int mVIewTYpeItem = -1;


    public BaseDelegetAdapter(Context context,
                               LayoutHelper layoutHelper,
                               int layoutId, int count ) {
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutId = layoutId;
        this.mContext = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
