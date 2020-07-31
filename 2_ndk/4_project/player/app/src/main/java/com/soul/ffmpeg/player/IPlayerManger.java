package com.soul.ffmpeg.player;

import android.view.SurfaceView;

/**
 * @描述：TODO
 * @作者：祝明
 * @项目名:ffmpeg
 * @创建时间：2020/5/29 0:51
 */
public interface IPlayerManger {

    void startPlay(String url);

    void audioDecode(String input, String output);

    void setSurfaceView(SurfaceView surfaceView);
}
