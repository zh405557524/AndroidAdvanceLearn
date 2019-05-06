package com.soul.adaptive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.soul.adaptive.ui.UIUtils;
import com.soul.adaptive.ui.ViewCalculateUtil;

public class MainActivity extends AppCompatActivity {

    private TextView mText3;
    private View mText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIUtils.getInstance(this);
        mText3 = findViewById(R.id.tvText3);
        mText4 = findViewById(R.id.tvText4);
        ViewCalculateUtil.setViewLinearLayoutParam(mText3, 540, 100, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(mText4, 1080, 100, 0, 0, 0, 0);
        ViewCalculateUtil.setTextSize(mText3, 30);

    }
}
