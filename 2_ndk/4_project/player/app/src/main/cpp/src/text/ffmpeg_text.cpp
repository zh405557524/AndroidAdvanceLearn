//
// Created by admin on 2020/7/31.
//

#include <libavformat/avformat.h>
#include "ffmpeg_text.h"


FFMPegText::FFMPegText() {

}


void FFMPegText::audioDecode(const char &input, const char &output) {

    //1 打开文件
    //初始化网络

    avformat_network_init();
    AVDictionary *opts = NULL;
    AVFormatContext *avFormatContext;

    int ret = av_dict_set(opts, "timeout", "3000000", 0);

    avformat_open_input()

}

