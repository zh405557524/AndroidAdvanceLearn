package com.soul.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_car).setOnClickListener(this);
        findViewById(R.id.bt_toolbar).setOnClickListener(this);
        findViewById(R.id.recyclerView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_car://旋转的小车
                startActivity(new Intent(MainActivity.this, CarActivity.class));
                break;
            case R.id.bt_toolbar://toolbar
                startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
                break;
            case R.id.recyclerView:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
        }
    }


}
























