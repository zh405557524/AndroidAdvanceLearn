package com.soul.alifix;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.result);
        final Caclutor caclutor = new Caclutor();

    }

    public void caculator(View view) {
        final Caclutor caclutor = new Caclutor();

        mTextView.setText("10");
    }


    public void fix(View view) {
        final File file = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        final DexManger dexManger = new DexManger(this);
        dexManger.load(file);
    }

}
