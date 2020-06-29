//
// Created by soul on 2020/6/1.
//

#ifndef FFMPEG_AUDIO_CHANNEL_H
#define FFMPEG_AUDIO_CHANNEL_H

#include <SLES/OpenSLES_Android.h>
#include "base_channel.h"


class AudioChannel : public BaseChannel {

public:

    AudioChannel(int channelId, AVCodecContext &avCodecContext);

public:

    virtual void synchronizeFrame() {
        initOpenSl();
    };

    /**
     * 初始化 openSl
     */
    void initOpenSl();

    int getPcm();

    virtual void stop() {

    }

public:
    uint8_t *buffer;
private:
    int out_channels;
    int out_sampleSize;
    int out_sample_rate;
    SwrContext *swr_ctx = NULL;

};


#endif //FFMPEG_AUDIO_CHANNEL_H
