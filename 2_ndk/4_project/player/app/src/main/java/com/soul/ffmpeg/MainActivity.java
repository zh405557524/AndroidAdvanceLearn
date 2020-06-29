package com.soul.ffmpeg;

import android.Manifest;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.soul.ffmpeg.player.PlayerManger;
import com.soul.ffmpeg.utils.PermissionsUtils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.

    private static final String path = "/sdcard/三上悠亜 無碼流出 無修正.mp4";



    String[] permissions = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        PlayerManger.getInstance().setSurfaceView(surfaceView);
        findViewById(R.id.start_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerManger.getInstance().startPlay(path);
            }

        });
        new ArrayList<>();
        PermissionsUtils.lacksPermissions(this, permissions);
    }

}
