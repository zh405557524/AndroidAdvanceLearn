package com.soul.pathmeasure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(this);
        setContentView(R.layout.activity_main);

        final RippleAnimationView rippleAnimationView = findViewById(R.id.rippleAnimationView);
        final Button startAnimal = findViewById(R.id.bt_startAnimal);
        startAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleAnimationView.start();
            }
        });
    }
}
