package com.soul.ffmpeg.player.ffmpeg;

import android.view.Surface;

/**
 * @描述：TODO
 * @作者：祝明
 * @项目名:ffmpeg
 * @创建时间：2020/5/28 13:28
 */
public class FFMPegPlayerMpl implements IPlayerMpl, OnPlayNativeCallBack {

    static {
        System.loadLibrary("player-lib");
    }

    private long mNativePlayInstance;

    @Override
    public void init() {
        mNativePlayInstance = nativeInit();
        setNativeCallBack(mNativePlayInstance, this);
    }


    @Override
    public void prepare() {
        nativePrepare(mNativePlayInstance);
    }

    @Override
    public void setPlayWindow(Surface surface) {
        setNativeWindow(mNativePlayInstance, surface);
    }

    @Override
    public void setPlayUrl(String url) {
        setNativePlayUrl(mNativePlayInstance, url);
    }


    @Override
    public void play() {
        nativePlay(mNativePlayInstance);
    }


    @Override
    public void pause() {
        nativePause(mNativePlayInstance);
    }


    @Override
    public void stop() {
        nativeStop(mNativePlayInstance);
    }


    @Override
    public void seekTo(int progress) {
        nativeSeekTo(mNativePlayInstance, progress);
    }


    private OnPrepareListener mOnPrepareListener;

    private OnErrorListener mOnErrorListener;

    private OnProgressListener mOnProgressListener;

    @Override
    public void setPrepareListener(OnPrepareListener onPrepareListener) {
        mOnPrepareListener = onPrepareListener;
    }

    @Override
    public void setErrorListener(OnErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    @Override
    public void setProgressListener(OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }

    @Override
    public void audioDecode(String input, String output) {
        nativeAudioDecode(input,output);
    }


    @Override
    public void onProgress(int progress) {
        if (mOnProgressListener != null) {
            mOnProgressListener.onProgress(progress);
        }
    }

    @Override
    public void onTotal(int total) {
        if (mOnProgressListener != null) {
            mOnProgressListener.onTotal(total);
        }
    }

    @Override
    public void onPrepare(int code) {
        if (mOnProgressListener != null) {
            mOnPrepareListener.onPrepare(code);
        }
    }

    @Override
    public void onError(int errorCode) {
        if (mOnErrorListener != null) {
            mOnErrorListener.onError(errorCode);
        }
    }

    private native long nativeInit();

    private native void setNativeCallBack(long nativePlayInstance, OnPlayNativeCallBack onPlayNativeCallBack);

    private native void setNativeWindow(long nativePlayInstance, Surface surface);

    private native void nativePrepare(long nativePlayInstance);

    private native void setNativePlayUrl(long nativePlayInstance, String url);

    private native void nativePlay(long nativePlayInstance);

    private native void nativePause(long nativePlayInstance);

    private native void nativeStop(long nativePlayInstance);

    private native void nativeSeekTo(long nativePlayInstance, int progress);

    private native void nativeAudioDecode(String input, String output);


}
