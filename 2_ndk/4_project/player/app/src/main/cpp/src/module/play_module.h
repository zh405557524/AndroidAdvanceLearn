//
// Created by soul on 2020/6/1.
//
#include <jni.h>
#include <string>
#include <player/player.h>

#ifndef FFMPEG_PLAY_MODULE_H
#define FFMPEG_PLAY_MODULE_H

/**
 * 播放控制公用类
 */


//播放器模块
class PlayModule : IPlay, PlayerListener {

private://成员变量

    player *m_player;
    /**
     * java层回调类
     */
    jobject m_javaListener;
    ANativeWindow *aNativeWindow;

public://构造方法

    PlayModule() {
        m_player = new player(this);
    }

    ~PlayModule() {
        delete m_player;
        if (aNativeWindow != nullptr) {
            ANativeWindow_release(aNativeWindow);
        }
    }

public://公共方法

    /**
     * 设置java层回调
     * @param nativeCallBack
     */
    void setNativeCallBack(jobject nativeCallBack) {
        m_javaListener = nativeCallBack;
    }

    /**
     * 准备工作
     */
    void prepare() {
        m_player->prepare();
    }

    /**
     * 设置播放窗口
     * @param window
     */
    void setNativeWindow(JNIEnv *jniEnv, jobject window) {
        aNativeWindow = ANativeWindow_fromSurface(jniEnv, window);
        m_player->setNativeWindow(*aNativeWindow);
    }

    /**
     * 设置播放地址
     * @param url
     */
    void setPlayUrl(std::string url) {
        m_player->setPlayUrl(url.c_str());
//        m_player->play(url, *aNativeWindow);
    }

public:

    /**
     * 播放
     */
    virtual void play() {
        m_player->play();
    }

    /**
     * 暂停
     */
    virtual void pause() {
        m_player->pause();
    }

    /**
     * 停止
     */
    virtual void stop() {
        m_player->stop();
    }

    /**
     * 滑动到指定位置
     * @param progress
     */
    virtual void seekTo(int progress) {
        m_player->seekTo(progress);
    }

public://回调参数

    virtual void onError(int errorCode);

    virtual void onProgress(int progress);

    virtual void onTotal(int total);

    virtual void onPrepare();
};


#endif //FFMPEG_PLAY_MODULE_H
