#include <jni.h>
#include <string>
#include "config.h"
#include "gif_lib.h"

typedef struct GifBean {

    //播放帧数 第几帧
    int current_frame;

    //总帧数
    int total_frame;

    //延迟时间数组，长度不确定 malloc
    int *dealys;

} GifBean;


//地址
extern "C"
JNIEXPORT jlong JNICALL
Java_com_soul_gifplay_GifHandler_loadPath(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    // 0 成功 ；非0 失败
    int err;
    //打开gif 文件
    GifFileType *gifFile = DGifOpenFileName(path, &err);

    //创建一个GifBean 对象
    GifBean *gifBean = (GifBean *) (malloc(sizeof(GifBean)));

    //数据绑定
    gifFile->UserData = gifBean;

    // 初始化数组 确定数组长度
    gifBean->dealys = (int *) malloc(sizeof(int) * gifFile->ImageCount);

    // 清空数据
    memset(gifBean, 0, sizeof(GifBean));

    //获取时间
    

    //刷新
    DGifSlurp(gifFile);
    //

    env->ReleaseStringUTFChars(path_, path);
    return (jlong) gifFile;
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