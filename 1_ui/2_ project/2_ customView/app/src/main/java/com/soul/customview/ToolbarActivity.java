package com.soul.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.soul.customview.view.Toolbar;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolbarActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        });
        toolbar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ToolbarActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        });

    }

}




































