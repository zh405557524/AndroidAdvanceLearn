package com.soul.materialdesign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soul.materialdesign.adapter.FeedAdapter;


/**
 * Description:recyclerView的用法
 * Author: 祝明
 * CreateDate:
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 */
public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        final FeedAdapter feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);
    }
}
