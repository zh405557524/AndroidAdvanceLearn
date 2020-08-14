//
// Created by admin on 2020/7/6.
//


#include "opensl.h"


OpenSl::OpenSl() {
}

void OpenSl::init() {
    LOGE("audio  iniOpenSL start");
    //音频引擎
    SLEngineItf engineInterface = NULL;
    //音频对此昂
    SLObjectItf engineObject = NULL;
    //混音器
    SLObjectItf outputMixObject = NULL;

    //播放器
    SLObjectItf bqPlayerObject = NULL;


    //    ----------------1-----初始化播放引擎-----------------------------
    SLresult result;
    result = slCreateEngine(&engineObject, 0, NULL, 0, NULL, NULL);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }
    result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }
//音频接口  相当于SurfaceHodler
    result = (*engineObject)->GetInterface(engineObject, SL_IID_ENGINE, &engineInterface);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }

    //    ---------2------------初始化播放引擎-----------------------------
    result = (*engineInterface)->CreateOutputMix(engineInterface, &outputMixObject, 0, 0, 0);
    // 初始化混音器outputMixObject
    result = (*outputMixObject)->Realize(outputMixObject, SL_BOOLEAN_FALSE);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }
    SLDataLocator_AndroidSimpleBufferQueue android_queue = {SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE,
                                                            2};
    //pcm数据格式
    SLDataFormat_PCM pcm = {SL_DATAFORMAT_PCM//播放pcm格式的数据
            , 2,//2个声道（立体声）
                            SL_SAMPLINGRATE_44_1, //44100hz的频率
                            SL_PCMSAMPLEFORMAT_FIXED_16,//位数 16位
                            SL_PCMSAMPLEFORMAT_FIXED_16,//和位数一致就行
                            SL_SPEAKER_FRONT_LEFT | SL_SPEAKER_FRONT_RIGHT,//立体声（前左前右）
                            SL_BYTEORDER_LITTLEENDIAN//小端模式
    };
    //
    SLDataLocator_OutputMix outputMix = {SL_DATALOCATOR_OUTPUTMIX, outputMixObject};

    SLDataSink audioSnk = {&outputMix, NULL};
    SLDataSource slDataSource = {&android_queue, &pcm};
    const SLInterfaceID ids[1] = {SL_IID_BUFFERQUEUE};
    const SLboolean req[1] = {SL_BOOLEAN_TRUE};
    (*engineInterface)->CreateAudioPlayer(engineInterface, &bqPlayerObject// //播放器
            , &slDataSource//播放器参数  播放缓冲队列   播放格式
            , &audioSnk,//播放缓冲区
                                          1,//播放接口回调个数
                                          ids,//设置播放队列ID
                                          req//是否采用内置的播放队列
    );
    //初始化播放器
    (*bqPlayerObject)->Realize(bqPlayerObject, SL_BOOLEAN_FALSE);
//bqPlayerObject   这个对象
//    得到接口后调用  获取Player接口
    (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_PLAY, &bqPlayerInterface);
//    获得播放器接口
    (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_BUFFERQUEUE,
                                    &bqPlayerBufferQueue);



}

void OpenSl::start() {

    //    设置播放状态
    (*bqPlayerInterface)->SetPlayState(bqPlayerInterface, SL_PLAYSTATE_PLAYING);
}

void OpenSl::setPlayerCallback(PlayerCallback playerCallback, void *context) {
    (*bqPlayerBufferQueue)->RegisterCallback(bqPlayerBufferQueue, playerCallback, context);

    playerCallback(bqPlayerBufferQueue, context);
}
