package com.soul.aop.aspectj;

import android.os.Bundle;
import android.util.Log;

import com.soul.aop.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("TAG", "LoginActivity");
    }
}
