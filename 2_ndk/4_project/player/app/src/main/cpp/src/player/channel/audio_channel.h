//
// Created by soul on 2020/6/1.
//

#ifndef FFMPEG_AUDIO_CHANNEL_H
#define FFMPEG_AUDIO_CHANNEL_H

#include <SLES/OpenSLES_Android.h>
#include "base_channel.h"


class AudioChannel : public BaseChannel {

public:

    AudioChannel();

public:

    virtual void readPacket();

    virtual void synchronizeFrame() {
        swr_ctx = swr_alloc_set_opts(0, AV_CH_LAYOUT_STEREO, AV_SAMPLE_FMT_S16, out_sample_rate,
                                     m_avCodecContext->channel_layout,
                                     m_avCodecContext->sample_fmt,
                                     m_avCodecContext->sample_rate, 0, 0);
        swr_init(swr_ctx);
        initOpenSl();

    };

    /**
     * 初始化 openSl
     */
    void initOpenSl();

    int getPcm();


    void stop() {
        isPlaying = false;
        pktQueue.clear();
        frameQueue.clear();
        swr_free(&swr_ctx);
        av_free(buffer);
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
