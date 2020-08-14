package com.soul.ffmpeg.player;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.soul.ffmpeg.player.ffmpeg.FFMPegPlayerMpl;
import com.soul.ffmpeg.player.ffmpeg.IPlayerMpl;

/**
 * @描述：TODO
 * @作者：祝明
 * @项目名:ffmpeg
 * @创建时间：2020/5/29 0:39
 */
public class PlayerManger implements SurfaceHolder.Callback, IPlayerManger {


    private static final String TAG = PlayerManger.class.getSimpleName();
    private IPlayerMpl mIPlayerMpl;
    private SurfaceHolder surfaceHolder;

    public static IPlayerManger getInstance() {
        return PlayerMangerHolder.sInstance;
    }


    private PlayerManger() {
        init();
    }

    private void init() {
        mIPlayerMpl = new FFMPegPlayerMpl();
        mIPlayerMpl.init();
        mIPlayerMpl.prepare();
        mIPlayerMpl.setPrepareListener(new IPlayerMpl.OnPrepareListener() {
            @Override
            public void onPrepare(int code) {
                Log.i(TAG, "onPrepare:" + code);
            }
        });
    }

    public void startPlay(String url) {
        if (surfaceHolder == null) {
            return;
        }
        mIPlayerMpl.setPlayWindow(surfaceHolder.getSurface());
        mIPlayerMpl.setPlayUrl(url);
        mIPlayerMpl.play();
        mIPlayerMpl.setProgressListener(new IPlayerMpl.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.i(TAG, "progress:" + progress);
            }

            @Override
            public void onTotal(int total) {
                Log.i(TAG, "onTotal:" + total);
            }
        });
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        if (null != this.surfaceHolder) {
            this.surfaceHolder.removeCallback(this);
        }
        this.surfaceHolder = surfaceView.getHolder();
        this.surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private static class PlayerMangerHolder {
        private static final IPlayerManger sInstance = new PlayerManger();
    }
}

