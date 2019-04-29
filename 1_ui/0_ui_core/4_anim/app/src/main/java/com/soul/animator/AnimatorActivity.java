package com.soul.animator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soul.animator.animator.LineInterpolator;
import com.soul.animator.animator.MyObjectAnimator;

public class AnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        final Button bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale(v);

            }
        });
    }
    public void scale(View view) {
        final MyObjectAnimator objectAnimator = MyObjectAnimator.ofFloat(view, "scaleX", 1f, 2f);
        objectAnimator.setTimeInterpolator(new LineInterpolator());
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

}
