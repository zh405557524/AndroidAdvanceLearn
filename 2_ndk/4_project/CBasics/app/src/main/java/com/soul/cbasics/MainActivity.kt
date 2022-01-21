package com.soul.cbasics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("cbasics-lib")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data_type.setOnClickListener { data_type() }

    }


    external fun data_type()

}
