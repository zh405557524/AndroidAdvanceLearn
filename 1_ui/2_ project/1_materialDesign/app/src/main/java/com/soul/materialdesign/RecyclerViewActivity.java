package com.soul.materialdesign;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.materialdesign.adapter.FeedAdapter;
import com.squareup.picasso.Picasso;


/**
 * Description:recyclerView的用法
 * Author: 祝明
 * CreateDate:
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private View mTitle;
    private int mPosition;
    private ImageView mIv_avatar;
    private TextView mTv_nickname;
    private int mMeasuredHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setStatusBar();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final FeedAdapter feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setHasFixedSize(true);

        mIv_avatar = findViewById(R.id.iv_avatar);
        mTv_nickname = findViewById(R.id.tv_nickname);

        mTitle = findViewById(R.id.rl_title);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setHeightAndPadding(RecyclerViewActivity.this, toolbar);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mMeasuredHeight = mTitle.getHeight();


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取下一个view
                //顶部如果小于或者等于条目的高度，则设置条目的位置
                final View nextView = linearLayoutManager.findViewByPosition(mPosition + 1);
                if (nextView != null && mMeasuredHeight >= nextView.getTop()) {
                    mTitle.setY(-(mMeasuredHeight - nextView.getTop()));
                } else {
                    mTitle.setY(0);
                }

                if (mPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    upDataTitle();
                }
            }
        });
        upDataTitle();

    }

    private void upDataTitle() {
        //设置头像
        Picasso.with(RecyclerViewActivity.this)
                .load(getAvatarUri(mPosition))
                .into(mIv_avatar);
        //设置名称
        mTv_nickname.setText("nickname:" + mPosition);

    }


    private int getAvatarUri(int position) {
        final int i = position % 4;
        int res = 0;
        switch (i) {
            case 0:
                res = R.drawable.avatar1;
                break;
            case 1:
                res = R.drawable.avatar2;
                break;
            case 2:
                res = R.drawable.avatar3;
                break;
            case 3:
                res = R.drawable.avatar4;
                break;
        }
        return res;
    }

    /**
     * 设置沉浸式
     */
    public void setStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色透明
            window.setStatusBarColor(Color.TRANSPARENT);

            int visibility = window.getDecorView().getSystemUiVisibility();
            //布局内容全屏展示
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //隐藏虚拟导航栏
            visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            //防止内容区域大小发生变化
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            window.getDecorView().setSystemUiVisibility(visibility);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setHeightAndPadding(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height += getStatusBarHeight(context);
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context), view.getPaddingRight(), view.getPaddingBottom());
        view.invalidate();
    }

    public int getStatusBarHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }
}
