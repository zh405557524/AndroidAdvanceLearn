#include <jni.h>
#include <string>
#include <module/play_module.h>
#include <text/ffmpeg_text.h>
#include "player.h"
#include "utils/jni_simple_type.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_soul_ffmpeg_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//初始化
extern "C"
JNIEXPORT jlong JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativeInit(JNIEnv *env, jobject thiz) {
    PlayModule *playModule = new PlayModule();
    return (jlong) (playModule);

}


//设置回调
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_setNativeCallBack(JNIEnv *env, jobject thiz,
                                                                     jlong native_play_instance,
                                                                     jobject callback) {
    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        playModule->setNativeCallBack(callback);
        return;
    }
}

//设置窗口
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_setNativeWindow(JNIEnv *env, jobject thiz,
                                                                   jlong native_play_instance,
                                                                   jobject surface) {

    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        playModule->setNativeWindow(env, surface);
        return;
    }
}

//开始准备
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativePrepare(JNIEnv *env, jobject thiz,
                                                                 jlong native_play_instance) {
    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        playModule->prepare();
        return;
    }

}

//设置播放地址
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_setNativePlayUrl(JNIEnv *env, jobject thiz,
                                                                    jlong native_play_instance,
                                                                    jstring url) {
    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        JNIString urlStr(env, url);
        playModule->setPlayUrl(urlStr.c_str());
        return;
    }
}

//播放
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativePlay(JNIEnv *env, jobject thiz,
                                                              jlong native_play_instance) {
    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        playModule->play();
        return;
    }

}

//暂停
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativePause(JNIEnv *env, jobject thiz,
                                                               jlong native_play_instance) {

}

//停止
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativeStop(JNIEnv *env, jobject thiz,
                                                              jlong native_play_instance) {
    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        playModule->stop();
        return;
    }
}


//滑动到指定位置
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativeSeekTo(JNIEnv *env, jobject thiz,
                                                                jlong native_play_instance,
                                                                jint progress) {
    PlayModule *playModule = (PlayModule *) native_play_instance;
    if (playModule != nullptr) {
        playModule->seekTo(progress);
        return;
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_soul_ffmpeg_player_ffmpeg_FFMPegPlayerMpl_nativeAudioDecode(JNIEnv *env, jobject instance,
                                                                     jstring input_,
                                                                     jstring output_) {
    const char *input = env->GetStringUTFChars(input_, 0);
    const char *output = env->GetStringUTFChars(output_, 0);

    FFMPegText *pText = new FFMPegText();
    pText->audioDecode(*input, *output);
    delete pText;
    env->ReleaseStringUTFChars(input_, input);
    env->ReleaseStringUTFChars(output_, output);
}