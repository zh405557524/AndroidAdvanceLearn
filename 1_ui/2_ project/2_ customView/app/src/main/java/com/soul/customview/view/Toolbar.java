package com.soul.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.customview.R;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-05-15 16:31
 * UpdateUser:
 * UpdateDate: 2019-05-15 16:31
 * UpdateRemark:
 */
public class Toolbar extends RelativeLayout {

    private ImageView iv_titlebar_left;
    private ImageView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private int mTextColor = Color.WHITE;
    private String titlename;

    public Toolbar(Context context) {
        super(context);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
        mTextColor = mTypedArray.getColor(R.styleable.ToolBar_title_text_color, Color.WHITE);
        titlename = mTypedArray.getString(R.styleable.ToolBar_title_text);
        //获取资源后要及时回收
        mTypedArray.recycle();
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);
        iv_titlebar_left = (ImageView) findViewById(R.id.iv_titlebar_left);
        iv_titlebar_right = (ImageView) findViewById(R.id.iv_titlebar_right);
        tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
        //设置标题文字颜色
        tv_titlebar_title.setTextColor(mTextColor);
        setTitle(titlename);

    }

    public void setLeftListener(OnClickListener onClickListener) {
        iv_titlebar_left.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        iv_titlebar_right.setOnClickListener(onClickListener);
    }

    public void setTitle(String titlename) {
        if (!TextUtils.isEmpty(titlename)) {
            tv_titlebar_title.setText(titlename);
        }
    }
}
