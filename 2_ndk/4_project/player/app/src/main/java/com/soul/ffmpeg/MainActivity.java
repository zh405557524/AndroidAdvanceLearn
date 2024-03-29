package com.soul.ffmpeg;

import android.Manifest;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.soul.ffmpeg.player.PlayerManger;
import com.soul.ffmpeg.system.Global;
import com.soul.ffmpeg.utils.PermissionsUtils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.

    private static final String path = "/sdcard/陈一发儿 - 童话镇.flac";

    private static final String input = "/sdcard/v0200f7b0000bs317v789gpio61jkqhg.MP4";
    private static final String output = "/sdcard/2.pcm";


    String[] permissions = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.setContext(getApplicationContext());
        setContentView(R.layout.activity_main);
        final SurfaceView surfaceView = findViewById(R.id.surfaceView);

        findViewById(R.id.start_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerManger.getInstance().setSurfaceView(surfaceView);
//                SystemManager.getInstance().requestAudioFocus();
                PlayerManger.getInstance().startPlay(path);
            }
        });


        new ArrayList();
        PermissionsUtils.lacksPermissions(this, permissions);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        SystemManager.getInstance().abandonAudioFocus();
    }
}
