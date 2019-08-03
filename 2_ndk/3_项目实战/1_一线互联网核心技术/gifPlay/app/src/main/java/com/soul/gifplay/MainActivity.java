package com.soul.gifplay;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private ImageView mImageView;
    private GifHandler mGifHandler;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image);

        findViewById(R.id.bt_loadGif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGif();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int nextFrame = mGifHandler.updateFrame(mBitmap);
            Log.i("Tag", "nextFrame:" + nextFrame);
            handler.sendEmptyMessageDelayed(1, nextFrame);
            mImageView.setImageBitmap(mBitmap);
        }
    };


    public void loadGif() {
        final File file = new File(Environment.getExternalStorageDirectory(), "demo.gif");
        mGifHandler = new GifHandler(file.getAbsolutePath());

        final int width = mGifHandler.getWidth();
        final int height = mGifHandler.getHeight();
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final int nextFrame = mGifHandler.updateFrame(mBitmap);
        handler.sendEmptyMessageDelayed(1, nextFrame);


    }

}
