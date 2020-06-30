//
// Created by soul on 2020/6/1.
//

#ifndef FFMPEG_BASE_CHANNEL_H
#define FFMPEG_BASE_CHANNEL_H

#include <utils/safe_queue.h>
#include <utils/msg_queue.h>
#include <zconf.h>
#include "player_listener.h"

extern "C" {
#include "libavcodec/avcodec.h"
#include <libswscale/swscale.h>
#include <libavutil/imgutils.h>
#include <libavformat/avformat.h>
#include <libswresample/swresample.h>
#include <libavutil/time.h>
}


class ChannelMsg {
public:
    enum MsgType {
        MSG_TYPE_PACKET,
        MSG_TYPE_FRAME
    };

    MsgType msgType;

    ChannelMsg(MsgType type) {
        msgType = type;
    }
};


class BaseChannel : public MsgDispatcher<ChannelMsg> {

public:
    volatile int m_channelId;
    SafeQueue<AVPacket *> pktQueue;
    SafeQueue<AVFrame *> frameQueue;
    MsgLooper<ChannelMsg> m_pkt_msg;
    MsgLooper<ChannelMsg> m_frame_msg;
    AVRational time_base;
    double clock = 0;

protected:
    AVCodecContext *m_avCodecContext;
    bool isPlaying;

public:

    BaseChannel(int channelId, AVCodecContext &avCodecContext, AVRational &time_base)
            : m_pkt_msg(this), m_frame_msg(this) {
        m_avCodecContext = &avCodecContext;
        this->time_base = time_base;
        m_channelId = channelId;
        pktQueue.setReleaseHandle(releaseAvPacket);
        frameQueue.setReleaseHandle(releaseAvFrame);
    }

    static void releaseAvPacket(AVPacket *&packet) {
        if (packet) {
            av_packet_free(&packet);
            packet = 0;
        }
    }

    static void releaseAvFrame(AVFrame *&frame) {
        if (frame) {
            av_frame_free(&frame);
            frame = 0;
        }
    }

    ~BaseChannel() {
        if (m_avCodecContext) {
            avcodec_close(m_avCodecContext);
            avcodec_free_context(&m_avCodecContext);
            m_avCodecContext = 0;
        }
        pktQueue.clear();
        frameQueue.clear();
    }


public:
    /**
    * 播放
    */
    void play() {
        LOGE("channel begin play");
        isPlaying = true;
        pktQueue.setWork(1);
        frameQueue.setWork(1);
        m_pkt_msg.sendMsg(new ChannelMsg(ChannelMsg::MSG_TYPE_PACKET));
        m_frame_msg.sendMsg(new ChannelMsg(ChannelMsg::MSG_TYPE_FRAME));
    }

    /**
     * 暂停
     */
    void pause() {

    };

    /**
     * 停止
     */
    virtual void stop() = 0;

    /**
     * 滑动到指定位置
     * @param progress
     */
    void seekTo(int progress) {

    };


public:
    bool dealMsg(ChannelMsg *playMsg) {
        switch (playMsg->msgType) {
            case ChannelMsg::MSG_TYPE_PACKET://数据包
                readPacket();
                break;
            case ChannelMsg::MSG_TYPE_FRAME://渲染
                LOGE("ChannelMsg MSG_TYPE_FRAME");
                synchronizeFrame();
                break;
        }
        return true;
    }


protected:

    virtual void readPacket() {
        if (m_avCodecContext == nullptr) {
            return;
        }
        LOGE("begin readPacket");
        AVPacket *packet = 0;

        while (isPlaying) {

            int ret = pktQueue.deQueue(packet);

            if (!isPlaying) {
                break;
            }

            avcodec_send_packet(m_avCodecContext, packet);
            releaseAvPacket(packet);
            if (ret == AVERROR(EAGAIN)) {
                //需要更多数据
                continue;
            } else if (ret < 0) {
                break;
            }
            AVFrame *frame = av_frame_alloc();
            ret = avcodec_receive_frame(m_avCodecContext, frame);
            frameQueue.enQueue(frame);
            while (frameQueue.size() > 100 && isPlaying) {
                usleep(1000 * 16);
                continue;
            }
        }
    }

    virtual void synchronizeFrame() = 0;

    virtual void release() {

    }


};

#endif //FFMPEG_BASE_CHANNEL_H
