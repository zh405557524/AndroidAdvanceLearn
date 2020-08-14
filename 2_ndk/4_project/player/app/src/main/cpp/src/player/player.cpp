//
// Created by soul on 2020/5/28.
//

#include <utils/log.h>
#include "player.h"


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
            if (m_formatContext) {
                playerListener->onPrepare(0);
            } else {
                playerListener->onPrepare(-1);
            }
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

                //3、获取对应的解码器并打开
                AVStream *stream = m_formatContext->streams[i];
                //找到解码参数
                AVCodecParameters *codecpar = m_formatContext->streams[i]->codecpar;
                //找到解码器
                AVCodec *dec = avcodec_find_decoder(codecpar->codec_id);
                AVCodecContext *codecContext = avcodec_alloc_context3(dec);
                //讲解码器参数copy到解码器上下文
                avcodec_parameters_to_context(codecContext, codecpar);

                //打开解码器
                ret = avcodec_open2(codecContext, dec, 0);
                if (ret != 0) {
                    return false;
                }
                if (!codecContext) {
                    return false;
                }

                //找到解码器,并打开解码器
                if (m_formatContext->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
                    AVRational frame_rate = stream->avg_frame_rate;
//视频
//            int fps = frame_rate.num / (double)frame_rate.den;
                    int fps = av_q2d(frame_rate);
                    //视频流
                    m_videoChannel = new VideoChannel(i, *codecContext,
                                                      m_formatContext->streams[i]->time_base);
                    m_videoChannel->setFps(fps);
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

            m_videoChannel->m_audioChannel = audioChannel;
            LOGE("msg begin play");
            m_videoChannel->play();
            audioChannel->play();
            int ret = 0;
            while (isPlaying) {

                if (m_videoChannel->pktQueue.size() > 100) {
                    usleep(1000 * 16);
                    continue;
                }

                if (audioChannel->pktQueue.size() > 100) {
                    usleep(1000 * 16);
                    continue;
                }

                //4、读取数据包
                AVPacket *packet = av_packet_alloc();
                ret = av_read_frame(m_formatContext, packet);
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
        case PlayMsg::MSG_TYPE_RELEASE://释放资源
        {
            if (m_videoChannel != nullptr) {
                m_videoChannel->stop();
                delete m_videoChannel;
            }
        }
            break;


    }
    return true;
}






