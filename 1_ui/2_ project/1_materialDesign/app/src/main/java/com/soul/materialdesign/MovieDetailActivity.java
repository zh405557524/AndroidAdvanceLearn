package com.soul.materialdesign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        String url = getIntent().getStringExtra("URL");
        String name = getIntent().getStringExtra("NAME");

        Toolbar toolbar = findViewById(R.id.tb_amd_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(name);
            setSupportActionBar(toolbar);
        }

        //使用CollapsingToolbarlayout必须把title设置到CollapsingToolBarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        //通过collapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没有收缩时状态
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
    }
}
