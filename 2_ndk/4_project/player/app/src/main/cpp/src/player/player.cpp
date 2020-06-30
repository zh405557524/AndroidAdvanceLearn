//
// Created by soul on 2020/5/28.
//

#include "player.h"


void player::play(std::string uri, ANativeWindow &aNativeWindow) {
    //1、打开视频文件
    avformat_network_init();
    AVFormatContext *formatContext = avformat_alloc_context();

    //打开 URL
    AVDictionary *opts = NULL;
    //设置超时3秒
    av_dict_set(&opts, "timeout", "3000000", 0);

    int ret = avformat_open_input(&formatContext, uri.c_str(), NULL, &opts);
    if (ret) {//ret为 0 则表示成功
        return;
    }
    //2、获取数据流
    avformat_find_stream_info(formatContext, NULL);
    //3、找到视频流和对应的解码器
    int video_stream_idx = -1;

    for (int i = 0; i < formatContext->nb_streams; ++i) {
        if (formatContext->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            video_stream_idx = i;
            break;
        }
    }
    //找到解码参数
    AVCodecParameters *codecPar = formatContext->streams[video_stream_idx]->codecpar;
    //找到解码器
    AVCodec *dec = avcodec_find_decoder(codecPar->codec_id);
    AVCodecContext *codecContext = avcodec_alloc_context3(dec);
    //将解码器参数copy到解码器上下文
    avcodec_parameters_to_context(codecContext, codecPar);
    //打开解码器
    avcodec_open2(codecContext, dec, NULL);

    //4、通过解码器读取数据 得到yuv数据

    //转化的上下文
    SwsContext *sws_ctx = sws_getContext(
            codecContext->width, codecContext->height,
            codecContext->pix_fmt,
            codecContext->width, codecContext->height,
            AV_PIX_FMT_RGBA,
            SWS_BICUBIC,//转化的模式，有质量优先，跟速度优先
            0, 0, 0);
    //设置窗口
    //视频缓冲区
    ANativeWindow_Buffer outBuffer;
    //创建新的窗口用于视频显示
    int frameCount = 0;
    ANativeWindow_setBuffersGeometry(&aNativeWindow, codecContext->width,
                                     codecContext->height,
                                     WINDOW_FORMAT_RGBA_8888);

    //读取包
    AVPacket *packet = av_packet_alloc();
    while (av_read_frame(formatContext, packet) >= 0) {
        avcodec_send_packet(codecContext, packet);
        AVFrame *frame = av_frame_alloc();
        ret = avcodec_receive_frame(codecContext, frame);

        if (ret == AVERROR(EAGAIN)) {
            //需要更多数据
            continue;
        } else if (ret < 0) { break; }

        //5、将yuv数据转化成grb数据
        //设置容器大小
        uint8_t *dst_data[0];
        int dst_line_size[0];
        //分配内存
        av_image_alloc(dst_data, dst_line_size, codecContext->width, codecContext->height,
                       AV_PIX_FMT_RGBA, 1);

        if (packet->stream_index == video_stream_idx && ret == 0) {
            //转化
            sws_scale(sws_ctx, reinterpret_cast<const uint8_t *const *>(frame->data),
                      frame->linesize, 0, frame->height, dst_data, dst_line_size);
            //渲染
            //6、将grb数据通过内存拷贝绘制在surfaceView中

            ANativeWindow_lock(&aNativeWindow, &outBuffer, NULL);
            //rgb_frame是有画面数据
            uint8_t *dst = (uint8_t *) outBuffer.bits;
            // 拿到一行有多少个字节 RGBA
            int destStride = outBuffer.stride * 4;
            uint8_t *src_data = dst_data[0];
            int src_line_size = dst_line_size[0];
            uint8_t *firstWindow = static_cast<uint8_t *>(outBuffer.bits);
            for (int i = 0; i < outBuffer.height; ++i) {
                //内存拷贝 来进行渲染
                memcpy(firstWindow + i * destStride, src_data + i * src_line_size, destStride);
            }
            ANativeWindow_unlockAndPost(&aNativeWindow);
            usleep(1000 * 16);
            av_frame_free(&frame);
        }
    }

    //7、释放资源
    avcodec_close(codecContext);
    avformat_free_context(formatContext);

}

void player::prepare() {
    PlayMsg *playMsg = new PlayMsg();
    playMsg->msgType = PlayMsg::MSG_TYPE_PREPARE;
    m_msg_queue.sendMsg(playMsg);
}

void player::setPlayUrl(const char *url) {
//    release();
    LOGE("setPlayUrl");
    PlayMsg *playMsg = new PlayMsg();
    playMsg->msgType = PlayMsg::MSG_TYPE_SETPLAYURL;
    playMsg->str = url;
    m_msg_queue.sendMsg(playMsg);
}


void player::release() {
    PlayMsg *playMsg = new PlayMsg();
    playMsg->msgType = PlayMsg::MSG_TYPE_RELEASE;
    m_msg_queue.sendMsg(playMsg);
}


void player::play() {
    LOGE("play");
    isPlaying = true;
    PlayMsg *playMsg = new PlayMsg();
    playMsg->msgType = PlayMsg::MSG_TYPE_PLAY;
    m_msg_queue.sendMsg(playMsg);
}

void player::pause() {

}

void player::stop() {

}

void player::seekTo(int progress) {

}


bool player::dealMsg(PlayMsg *playMsg) {
    if (playMsg == nullptr) {
        LOGE("playMsg is null");
        return false;
    }

    switch (playMsg->msgType) {
        case PlayMsg::MSG_TYPE_PREPARE://准备
        {
            //1 开始准备工作 ，初始化网络
            avformat_network_init();
            //创建数据流的上下文
            m_formatContext = avformat_alloc_context();
        }
            break;
        case PlayMsg::MSG_TYPE_SETPLAYURL://设置地址
        {
            //1 打开 URL
            AVDictionary *opts = NULL;
            //设置超时3秒
            av_dict_set(&opts, "timeout", "3000000", 0);

            int ret = avformat_open_input(&m_formatContext, (playMsg->str).c_str(), NULL, &opts);

            if (ret) {//ret为 0 则表示成功
                LOGE("open file fail");
                return true;
            }

            //2 获取数据流
            avformat_find_stream_info(m_formatContext, NULL);

            for (int i = 0; i < m_formatContext->nb_streams; ++i) {
                //找到解码参数
                AVCodecParameters *codecpar = m_formatContext->streams[i]->codecpar;
                //找到解码器
                AVCodec *dec = avcodec_find_decoder(codecpar->codec_id);
                AVCodecContext *codecContext = avcodec_alloc_context3(dec);
                //讲解码器参数copy到解码器上下文
                avcodec_parameters_to_context(codecContext, codecpar);
                //打开解码器
                avcodec_open2(codecContext, dec, NULL);
                //读取包
                AVPacket *packet = av_packet_alloc();

                // 从媒体中读取音频、视频包
                ret = av_read_frame(m_formatContext, packet);
                if (ret) {
                    LOGE("read packet fail");
                }

                //找到解码器,并打开解码器
                if (m_formatContext->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
                    //视频流
                    m_videoChannel = new VideoChannel(i, *codecContext,
                                                      m_formatContext->streams[i]->time_base);
                    m_videoChannel->prepare(*m_aNativeWindow);
                } else if (m_formatContext->streams[i]->codecpar->codec_type ==
                           AVMEDIA_TYPE_AUDIO) {
                    //音频流
                    audioChannel = new AudioChannel(i, *codecContext,
                                                    m_formatContext->streams[i]->time_base);
                }
            }
        }
            break;
        case PlayMsg::MSG_TYPE_RELEASE://释放资源
        {
            if (m_videoChannel != nullptr) {
                m_videoChannel->stop();
                delete m_videoChannel;
            }
        }
            break;
        case PlayMsg::MSG_TYPE_PLAY://播放
        {
            if (m_videoChannel == nullptr) {
                LOGE("m_videoChannel is null");
                return true;
            }
            if (audioChannel == nullptr) {
                LOGE("audioChannel is null");
                return true;
            }

            LOGE("msg begin play");
            m_videoChannel->play();
            audioChannel->play();
            while (isPlaying) {

                if (m_videoChannel != nullptr && m_videoChannel->pktQueue.size() > 100) {
                    usleep(1000 * 16);
                    continue;
                }
                //读取包
                AVPacket *packet = av_packet_alloc();
                int ret = av_read_frame(m_formatContext, packet);
                if (ret == 0) {
                    //将数据包添加到队列中
                    if (packet->stream_index == m_videoChannel->m_channelId) {
                        //视频流
                        m_videoChannel->pktQueue.enQueue(packet);
                    } else if (packet->stream_index == audioChannel->m_channelId) {
                        audioChannel->pktQueue.enQueue(packet);
                    }

                } else if (ret == AVERROR_EOF) {
                    LOGE("msg play error");
                    //读取完毕 不一定是播放完毕
                    if (m_videoChannel->pktQueue.empty() && m_videoChannel->frameQueue.empty()) {
                        LOGE("msg played");
                        break;
                    }
                } else {
                    break;
                }
            }
            LOGE("msg play stop");
            isPlaying = 0;
            m_videoChannel->stop();
            audioChannel->stop();
        }
            break;
        case PlayMsg::MSG_TYPE_PAUSE://暂停
            break;
        case PlayMsg::MSG_TYPE_STOP://停止
            break;
        case PlayMsg::MSG_TYPE_SEEKTO://滑动指定的地方
            break;


    }
    return true;
}





