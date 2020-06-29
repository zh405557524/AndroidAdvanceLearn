package com.soul.ffmpeg.player.ffmpeg;

/**
 * @描述：natvie层回调
 * @作者：祝明
 * @项目名:ffmpeg
 * @创建时间：2020/6/1 13:54
 */
public interface OnPlayNativeCallBack {

    /**
     * 进度
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


    /**
     * 准备完成
     *
     * @param code
     */
    void onPrepare(int code);


    /**
     * 错误回调
     *
     * @param errorCode
     */
    void onError(int errorCode);


}
