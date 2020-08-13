//
// Created by soul on 2020/6/1.
//

#include <unistd.h>
#include "video_channel.h"

VideoChannel::VideoChannel(int channelId, AVCodecContext &aVCodecContext, AVRational &time_base)
        : BaseChannel(channelId, aVCodecContext, time_base) {
}


void VideoChannel::readPacket() {
    if (m_avCodecContext == nullptr) {
        return;
    }
    LOGE("begin readPacket");
    //子线程
    AVPacket *packet = 0;
    while (isPlaying) {
//        流 --packet  ---音频 视频     可以   单一
        int ret = pktQueue.deQueue(packet);
        if (!isPlaying) {
            break;
        }
        if (!ret) {
            continue;
        }
        //5、将数据包发送给解码器
        ret = avcodec_send_packet(m_avCodecContext, packet);
        releaseAvPacket(packet);
        if (ret == AVERROR(EAGAIN)) {
            //需要更多数据
            continue;
        } else if (ret < 0) {
            //失败  直播  端
            break;
        }
        AVFrame *frame = av_frame_alloc();
        //6、解码获取到原始数据 yuv数据 转换为 rgb8888
//       AVFrame  yuvi420   nv21  --->rgb8888
        ret = avcodec_receive_frame(m_avCodecContext, frame);
//        压缩数据        要解压
        frameQueue.enQueue(frame);
        while (frameQueue.size() > 100 && isPlaying) {
            av_usleep(1000 * 10);
            continue;
        }
    }
//    保险起见
    releaseAvPacket(packet);

}

void VideoChannel::synchronizeFrame() {

    LOGE("begin synchronizeFrame");
//初始换
    SwsContext *sws_ctx = sws_getContext(
            m_avCodecContext->width, m_avCodecContext->height, m_avCodecContext->pix_fmt,
            m_avCodecContext->width, m_avCodecContext->height, AV_PIX_FMT_RGBA,
            SWS_BILINEAR, 0, 0, 0);

    //1s
    uint8_t *dst_data[4]; //argb
    int dst_linesize[4];
    av_image_alloc(dst_data, dst_linesize,
                   m_avCodecContext->width, m_avCodecContext->height, AV_PIX_FMT_RGBA, 1);
    AVFrame *frame = 0;

    while (isPlaying) {
        int ret = frameQueue.deQueue(frame);
        if (!isPlaying) {
            break;
        }
        if (!ret) {
            continue;
        }
        sws_scale(sws_ctx,
                  reinterpret_cast<const uint8_t *const *>(frame->data), frame->linesize, 0,
                  frame->height,
                  dst_data, dst_linesize);
        frame->pts;
        //当前视频时间戳
        clock = frame->pts * av_q2d(time_base);

        renderFrame(dst_data[0], dst_linesize[0], m_avCodecContext->width,
                    m_avCodecContext->height);


//        解码时间算进去
        double frame_delays = 1.0 / fps;

        //当前音频时间戳
        double audioClock = m_audioChannel->clock;
//        将解码所需要的时间算进去  因为配置差的手机 解码耗时需要多一些
        double extra_delay = frame->repeat_pict / (2 * fps);
        double delay = extra_delay + frame_delays;

        double diff = clock - audioClock;
        LOGI("video_channel_diff:%f   clock:%f  audioClock:%f ", diff, clock, audioClock);
        do {

            if (clock > audioClock) {//视频超前
//        视频超前
                if (diff > 1) {
                    //差的太久了， 那只能慢慢赶 不然就是卡好久
                    av_usleep((delay * 2) * 1000000);
                    break;
                }
                av_usleep((delay + diff) * 1000000);
                break;
            }

            //视频延后
            if (diff > 1) {
//                不休眠
            } else if (diff >= 0.05) {
//                救一下
//视频需要追赶     丢帧  同步
                releaseAvFrame(frame);
                frameQueue.sync();
//                减少延迟时间
                //执行同步操作 删除到最近的key frame
                break;
            }

        } while (false);

        releaseAvFrame(frame);
    }
    av_freep(&dst_data[0]);
    isPlaying = false;
    releaseAvFrame(frame);
    sws_freeContext(sws_ctx);
}


void VideoChannel::renderFrame(uint8_t *data, int linesize, int w, int h) {
//    渲染

    ANativeWindow_Buffer window_buffer;
    if (ANativeWindow_lock(m_aNativeWindow, &window_buffer, 0)) {
        ANativeWindow_release(m_aNativeWindow);
        m_aNativeWindow = 0;
        return;
    }
//    缓冲区  window_data[0] =255;
    uint8_t *window_data = static_cast<uint8_t *>(window_buffer.bits);
//    r     g   b  a int
    int window_linesize = window_buffer.stride * 4;
    uint8_t *src_data = data;
    for (int i = 0; i < window_buffer.height; ++i) {
        memcpy(window_data + i * window_linesize, src_data + i * linesize, window_linesize);
    }
    ANativeWindow_unlockAndPost(m_aNativeWindow);
}

void VideoChannel::stop() {

}

void VideoChannel::setFps(int fps) {
    this->fps = fps;
}


