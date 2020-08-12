//
// Created by soul on 2020/6/1.
//

#ifndef FFMPEG_VIDEO_CHANNEL_H
#define FFMPEG_VIDEO_CHANNEL_H

#include <android/native_window_jni.h>
#include "base_channel.h"


class VideoChannel : public BaseChannel {
private:
    ANativeWindow *m_aNativeWindow;
public:
    VideoChannel(int channelId, AVCodecContext &avCodecContext, AVRational &time_base);

public:
    void prepare(ANativeWindow &aNativeWindow) {
        m_aNativeWindow = &aNativeWindow;
    }

    /**
     * 渲染画面
     * @param data
     * @param linesize
     * @param w
     * @param h
     */
    void renderFrame(uint8_t *data, int linesize, int w, int h);


    /**
     * 绘制frame 到屏幕
     */
    virtual void synchronizeFrame();

    virtual void readPacket();

    /**
    * 停止
    */
    virtual void stop();
};


#endif //FFMPEG_VIDEO_CHANNEL_H
