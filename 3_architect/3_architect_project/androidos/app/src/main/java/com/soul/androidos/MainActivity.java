package com.soul.androidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soul.androidos.handler.HandlerActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_handler).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_handler:
                startActivity(new Intent(MainActivity.this, HandlerActivity.class));
                break;
        }
    }
}
