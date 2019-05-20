package com.soul.customview.view.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-17 16:05
 * UpdateUser:
 * UpdateDate: 2019-05-17 16:05
 * UpdateRemark:
 */
public class RecyclerView extends ViewGroup {


    private Adapter mAdapter;


    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }


    @Override
    public void scrollBy(int x, int y) {

    }

    public interface Adapter {
        View onCreateViewHolder(int position, View convertView, ViewGroup parent);

        View onBinderViewHolder(int position, View convertView, ViewGroup parent);

        /**
         * item的类型
         *
         * @param row
         * @return
         */
        int getItemViewType(int row);

        /**
         * item 的类型数量
         *
         * @return
         */
        int getVIewTypeCount();

        int getCount();

    }


}










