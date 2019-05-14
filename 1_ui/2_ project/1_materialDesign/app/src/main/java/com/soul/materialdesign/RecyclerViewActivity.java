package com.soul.materialdesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final FeedAdapter feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);

        mIv_avatar = findViewById(R.id.iv_avatar);
        mTv_nickname = findViewById(R.id.tv_nickname);

        mTitle = findViewById(R.id.rl_title);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mMeasuredHeight = mTitle.getMeasuredHeight();


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
}
