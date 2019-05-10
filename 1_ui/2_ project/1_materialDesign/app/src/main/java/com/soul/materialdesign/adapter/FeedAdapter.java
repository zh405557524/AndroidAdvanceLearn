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


    }

    private int getAvatarUri(int position) {

        return 0;
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
