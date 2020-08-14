#include <jni.h>
#include <string>
#include <module/play_module.h>
#include "player.h"
#include "utils/jni_simple_type.h"
#include "ffmpeg_text.h"

JavaVM* JNIJavaVM::s_jvm = nullptr;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
#ifdef NATIVE_CLIENT_VERSION_INFO
    LOGD("VersionInfo : %s", NATIVE_CLIENT_VERSION_INFO);
#endif
    LOGD("JNI_OnLoad");

    JNIEnv *env = NULL;
    jint result = -1;

    JNIJavaVM::setJVM(vm);

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }

    // 返回jni的版本
    return JNI_VERSION_1_6;
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
        playModule->setNativeCallBack(env, callback);
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

    FFMpegText *pText = new FFMpegText();

    pText->decodeAudio(input, output);
    delete pText;


    env->ReleaseStringUTFChars(input_, input);
    env->ReleaseStringUTFChars(output_, output);
}