//
// Created by admin on 2020/7/6.
//

#ifndef FFMPEG_OPENSL_H
#define FFMPEG_OPENSL_H


#include <SLES/OpenSLES.h>
#include <opencl-c-base.h>
#include <SLES/OpenSLES_Android.h>
#include <utils/log.h>

typedef void (*PlayerCallback)(SLAndroidSimpleBufferQueueItf bq, void *context);

class OpenSl {

public:

    OpenSl();

    ~OpenSl() {

    }

public:
    void init();

    void start();

    void setPlayerCallback(PlayerCallback playerCallback, void *context);

private:

//    缓冲队列
    SLAndroidSimpleBufferQueueItf bqPlayerBufferQueue;

//    回调接口
    SLPlayItf bqPlayerInterface;
};


#endif //FFMPEG_OPENSL_H
