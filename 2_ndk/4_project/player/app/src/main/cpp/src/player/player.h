//
// Created by soul on 2020/5/28.
//

#ifndef FFMPEG_PLAYER_H
#define FFMPEG_PLAYER_H

#include <string>
#include <android/native_window_jni.h>
#include <zconf.h>
#include <android/native_window.h>
#include <player/channel/video_channel.h>
#include <utils/msg_queue.h>
#include "audio_channel.h"

class IPlay {

    /**
     * 播放
     */
    virtual void play() = 0;

    /**
     * 暂停
     */
    virtual void pause() = 0;

    /**
     * 停止
     */
    virtual void stop() = 0;

    /**
     * 滑动到指定位置
     * @param progress
     */
    virtual void seekTo(int progress) = 0;

};


class PlayMsg {
public:
    PlayMsg() {
    }


public:
    enum MsgType {
        MSG_TYPE_PREPARE,//准备
        MSG_TYPE_SETPLAYURL,//设置地址
        MSG_TYPE_RELEASE,//释放资源
        MSG_TYPE_PLAY,//播放
        MSG_TYPE_PAUSE,//暂停
        MSG_TYPE_STOP,//停止
        MSG_TYPE_SEEKTO//滑动指定的地方
    };
public:
    MsgType msgType;
    std::string str;


};


class player : public MsgDispatcher<PlayMsg>, public IPlay {

private:
    ANativeWindow *m_aNativeWindow;
    AVFormatContext *m_formatContext;
    AVCodecContext *codecContext;
    VideoChannel *m_videoChannel;
    AudioChannel *audioChannel;
    PlayerListener *playerListener;
    bool isPlaying;

    MsgLooper<PlayMsg> m_msg_queue;

public:

    player(PlayerListener *playerListener) : m_msg_queue(this) {
        this->playerListener = playerListener;
    }

    ~player() {
        delete m_formatContext;
    }

public:

    void prepare();

    void setNativeWindow(ANativeWindow &aNativeWindow) {
        m_aNativeWindow = &aNativeWindow;
    }

    void setPlayUrl(const char *url);

    void release();

public:
    /**
      * 播放
      */
    virtual void play();

    /**
     * 暂停
     */
    virtual void pause();

    /**
     * 停止
     */
    virtual void stop();

    /**
     * 滑动到指定位置
     * @param progress
     */
    virtual void seekTo(int progress);

public:
    bool dealMsg(PlayMsg *playMsg);

};


#endif //FFMPEG_PLAYER_H
