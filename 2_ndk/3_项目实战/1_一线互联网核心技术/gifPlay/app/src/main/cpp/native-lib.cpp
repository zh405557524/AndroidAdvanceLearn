#include <jni.h>
#include <string>
#include "config.h"
#include "gif_lib.h"

//地址
extern "C"
JNIEXPORT jlong JNICALL
Java_com_soul_gifplay_GifHandler_loadPath(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    int err;
    //打开gif 文件
    DGifOpenFileName(path, &err);
    //刷新
//    DGifSlurp()

    env->ReleaseStringUTFChars(path_, path);
}

//获取宽度
extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_gifplay_GifHandler_getWidth(JNIEnv *env, jobject instance, jlong ndkGif) {


}

//获取高度
extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_gifplay_GifHandler_getHeight(JNIEnv *env, jobject instance, jlong ndkGif) {


}

//绘制图片
extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_gifplay_GifHandler_updateFrame(JNIEnv *env, jobject instance, jlong ndkGif,
                                             jobject bitmap) {


}