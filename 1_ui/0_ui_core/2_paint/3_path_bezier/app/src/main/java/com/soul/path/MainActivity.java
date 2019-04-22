package com.soul.path;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button);
        final DragBubbleView dragBubbleView = findViewById(R.id.bubbleView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dragBubbleView.reset();
            }
        });
    }
}
