package com.soul.pathmeasure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_ripple).setOnClickListener(this);
        findViewById(R.id.bt_loading).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ripple://水波纹
                startActivity(new Intent(MainActivity.this, RippleActivity.class));
                break;
            case R.id.bt_loading://加载状态
                startActivity(new Intent(MainActivity.this, LoadingActivity.class));
                break;
        }
    }
}
