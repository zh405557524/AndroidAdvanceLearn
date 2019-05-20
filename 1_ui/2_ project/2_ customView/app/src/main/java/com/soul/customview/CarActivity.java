package com.soul.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.soul.customview.view.CarView;

public class CarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CarView(CarActivity.this));
    }
}
