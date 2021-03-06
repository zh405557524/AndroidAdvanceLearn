package com.soul.materialdesign.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.materialdesign.R;
import com.squareup.picasso.Picasso;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/5/10 下午5:27
 * UpdateUser:
 * UpdateDate: 2019/5/10 下午5:27
 * UpdateRemark:
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final FeedHolder feedHolder = new FeedHolder(View.inflate(viewGroup.getContext(), R.layout.item_feed, null));
        return feedHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int i) {
        //设置头像
        Picasso.with(feedHolder.iv_avatar.getContext())
                .load(getAvatarUri(i))
                .into(feedHolder.iv_avatar);
        //设置名称
        feedHolder.tv_nickname.setText("nickname:" + i);
        //设置图片
        Picasso.with(feedHolder.iv_content.getContext())
                .load(getContent(i))
                .into(feedHolder.iv_content);

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

    private int getContent(int position) {
        final int i = position % 4;
        int res = 0;
        switch (i) {
            case 0:
                res = R.drawable.taeyeon_one;
                break;
            case 1:
                res = R.drawable.taeyeon_two;
                break;
            case 2:
                res = R.drawable.taeyeon_three;
                break;
            case 3:
                res = R.drawable.taeyeon_four;
                break;
        }
        return res;
    }


    @Override
    public int getItemCount() {
        return 10;
    }


    public class FeedHolder extends RecyclerView.ViewHolder {

        public ImageView iv_avatar;//头像
        public TextView tv_nickname;//昵称
        public ImageView iv_content;//内容

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            iv_content = itemView.findViewById(R.id.iv_content);
        }

    }


}
