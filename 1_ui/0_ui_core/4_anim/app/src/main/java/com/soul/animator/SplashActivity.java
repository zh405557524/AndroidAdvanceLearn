package com.soul.animator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ParallaxContainer container = findViewById(R.id.parallax_container);
        container.setUp(new int[]{
                R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_login

        });
    }
}
