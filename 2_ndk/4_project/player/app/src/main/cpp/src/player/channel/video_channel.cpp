//
// Created by soul on 2020/6/1.
//

#include <unistd.h>
#include "video_channel.h"

VideoChannel::VideoChannel(int channelId, AVCodecContext &aVCodecContext, AVRational &time_base)
        : BaseChannel(channelId, aVCodecContext, time_base) {

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
        renderFrame(dst_data[0], dst_linesize[0], m_avCodecContext->width,
                    m_avCodecContext->height);
        usleep(1000 * 16);
        releaseAvFrame(frame);
    }
    av_freep(&dst_data[0]);
    isPlaying = false;
    releaseAvFrame(frame);
    sws_freeContext(sws_ctx);
}


void VideoChannel::renderFrame(uint8_t *data, int linesize, int w, int h) {
//    渲染
    //设置窗口属性
    ANativeWindow_setBuffersGeometry(m_aNativeWindow, w,
                                     h,
                                     WINDOW_FORMAT_RGBA_8888);

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

