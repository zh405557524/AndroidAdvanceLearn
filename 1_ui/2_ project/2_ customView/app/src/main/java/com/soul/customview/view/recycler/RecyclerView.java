package com.soul.customview.view.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.soul.customview.R;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 当前显示的View
     */
    private List<View> viewList;

    /**
     * 当前滑动的y值
     */
    private int currentY;

    /**
     * 行数
     */
    private int rowCount;

    /**
     * view 的第一行  是占内容的几行
     */
    private int firstRow;

    /**
     * y偏移量
     */
    private int scrollY;

    /**
     * 初始化 第一屏最慢
     */
    private boolean needRelayout;

    private int width;

    private int height;

    private int[] heights;//item 高度

    Recycler recycler;

    /**
     * 最小滑动距离
     */
    private int touchSlop;


    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            recycler = new Recycler(mAdapter.getVIewTypeCount());
            scrollY = 0;
            firstRow = 0;
            needRelayout = true;
            requestLayout();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            if (mAdapter != null) {
                //摆放
                width = r - 1;
                height = b - t;

                int left, top = 0, right, bottom;
                for (int i = 0; i < rowCount && top < height; i++) {
                    right = width;
                    bottom = top + heights[i];
                    makeAndStop(i, 0, top, width, bottom);
                    //生成一个View
                    top = bottom;
                }

            }
        }
    }

    private void makeAndStop(int row, int left, int top, int right, int bottom) {
        View view = obtainView(row, right - left, bottom - top);
        view.layout(left, top, right, bottom);
    }

    private View obtainView(int row, int i, int i1) {
        //key type
        final int itemType = mAdapter.getItemViewType(row);
        final View recyclerView = recycler.get(itemType);
        View view = null;
        if (recyclerView == null) {
            view = mAdapter.onCreateViewHolder(row, recyclerView, this);
            if (view == null) {
                throw new RuntimeException("onCreateViewHolder 必须填充布局");
            }
        } else {
            view = mAdapter.onBinderViewHolder(row, recyclerView, this);
        }
        view.setTag(R.id.tag_type_view, itemType);
        view.measure(MeasureSpec.makeMeasureSpec(i, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(i1, MeasureSpec.EXACTLY));
        addView(view, 0);

        return view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h = 0;
        if (mAdapter != null) {
            this.rowCount = mAdapter.getCount();
            heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = mAdapter.getHeight(i);
            }
        }
        int tmpH = sumArray(heights, 0, heights.length);
        h = Math.min(heightSize, tmpH);
        setMeasuredDimension(widthSize, h);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y2 = Math.abs(currentY - (int) ev.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
                break;
        }
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //移动的距离 y方向
                int y2 = (int) event.getRawY();

                final int diffY = currentY - y2;
                scrollBy(0, diffY);
                break;
        }


        return super.onTouchEvent(event);
    }


    @Override
    public void scrollBy(int x, int y) {
        scrollY += y;
        //        scrollY = scrollBounds(scrollY);
        Log.i("Tag", "scrollY:" + scrollY);
        if (scrollY > 0) {
            //上滑正 下滑负 边界值
            while (scrollY > heights[firstRow]) {
                //1 上滑移除 2 上滑加载  3 下滑移除 4 下滑加载
                removeView(viewList.remove(0));
                scrollY -= heights[firstRow];
                firstRow++;
            }

            while (getFillHeight() < height) {
                int addLast = firstRow + viewList.size();
                final View view = obtainView(addLast, width, heights[addLast]);
                viewList.add(viewList.size(), view);
            }

        } else if (scrollY < 0) {
            //下滑加载
            while (scrollY < 0) {
                int firstAddRow = firstRow - 1;
                final View view = obtainView(firstAddRow, width, heights[firstAddRow]);
                viewList.add(0, view);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }

            while (sumArray(heights, firstRow, viewList.size()) - scrollY - heights[firstRow + viewList.size()] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }

        }

        repositionViews();
    }

    private void repositionViews() {
        int left, top, right, bottom, i;
        top = -scrollY;
        i = firstRow;
        for (View view : viewList) {
            bottom = top + heights[i++];
            view.layout(0, top, width, bottom);
            top = bottom;
        }

    }

    private int getFillHeight() {


        return sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    private int scrollBounds(int scrollY) {
        //上滑
        if (scrollY > 0) {


        } else {
            //极限值 会取零  非极限值的情况下 scrollY
            scrollY = Math.max(scrollY, -sumArray(heights, 0, firstRow));
        }
        return scrollY;

    }


    @Override
    public void removeView(View view) {
        super.removeView(view);
        final int key = (int) view.getTag(R.id.tag_type_view);
        recycler.put(view, key);
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

        int getHeight(int index);

    }


}










