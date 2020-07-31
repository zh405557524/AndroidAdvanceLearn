package com.soul.ffmpeg.player.ffmpeg;

import android.view.Surface;

/**
 * @描述：播放功能实现类
 * @作者：祝明
 * @项目名:ffmpeg
 * @创建时间：2020/5/28 13:28
 */
public interface IPlayerMpl {


    /**
     * 初始化
     */
    void init();

    /**
     * 播放前准备
     */
    void prepare();

    void setPlayWindow(Surface surface);

    /**
     * 设置播放地址
     *
     * @param url
     */
    void setPlayUrl(String url);


    /**
     * 播放
     */
    void play();

    /**
     * 暂停
     */
    void pause();

    /**
     * 停止播放，释放资源，重新播放则重新播放
     */
    void stop();

    /**
     * 滑动至播放的位置
     *
     * @param progress 默认是1~100
     */
    void seekTo(int progress);

    /**
     * 设置准备状态监听
     *
     * @param onPrepareListener
     */
    void setPrepareListener(OnPrepareListener onPrepareListener);

    /**
     * 设置错误监听
     *
     * @param onErrorListener
     */
    void setErrorListener(OnErrorListener onErrorListener);


    /**
     * 设置进度回调监听
     *
     * @param onProgressListener
     */
    void setProgressListener(OnProgressListener onProgressListener);

    void audioDecode(String input, String output);


    /**
     * 准备监听
     */
    interface OnPrepareListener {
        /**
         * 准备监听回调
         *
         * @param code 0-表示成功 ;小于0表示失败
         */
        void onPrepare(int code);
    }

    /**
     * 播放回调
     */
    interface OnErrorListener {
        /**
         * 播放错误回调
         *
         * @param errorCode
         */
        void onError(int errorCode);
    }

    /**
     * 进度监听
     */
    interface OnProgressListener {
        /**
         * 播放进度
         *
         * @param progress
         */
        void onProgress(int progress);

        /**
         * 总进度
         *
         * @param total
         */
        void onTotal(int total);
    }


}
