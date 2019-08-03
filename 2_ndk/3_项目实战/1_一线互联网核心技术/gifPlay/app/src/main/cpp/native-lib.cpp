#include <jni.h>
#include <string>
#include "config.h"
#include "gif_lib.h"
#include <android/log.h>
#include <android/bitmap.h>

#define  argb(a, r, g, b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

typedef struct GifBean {

    //播放帧数 第几帧
    int current_frame;

    //总帧数
    int total_frame;

    //延迟时间数组，长度不确定 malloc
    int *dealys;

} GifBean;


void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels) {
    //当前帧
    SavedImage savedImage = gifFileType->SavedImages[gifBean->current_frame];
    int *px = (int *) pixels;
    int pointPixel;

    GifByteType gifFIleType;//压缩数据

    GifImageDesc &frameInfo = savedImage.ImageDesc;

    //rgb数据  压缩工具
    ColorMapObject *colorMapObject = frameInfo.ColorMap;

    px = (int *) ((char *) px + info.stride * frameInfo.Top);

    int *line;

    for (int y = frameInfo.Top; y < frameInfo.Top + frameInfo.Height; ++y) {
        line = px;
        for (int x = frameInfo.Left; x < frameInfo.Left + frameInfo.Width; ++x) {
            //拿到每一个坐标的位置，索引 ---》数据
            pointPixel = (y - frameInfo.Top) * frameInfo.Width + (x - frameInfo.Left);
            //索引 rgb LZW 压缩  字典 ()缓存在一个字典

            //解压
            gifFIleType = savedImage.RasterBits[pointPixel];
            GifColorType &gifColorType = colorMapObject->Colors[gifFIleType];
            line[x] = argb(255, gifColorType.Red, gifColorType.Green, gifColorType.Blue);

        }
        px = (int *) ((char *) px + info.stride);
    }

}

//地址
extern "C"
JNIEXPORT jlong JNICALL
Java_com_soul_gifplay_GifHandler_loadPath(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    // 0 成功 ；非0 失败
    int err;
    //打开gif 文件
    GifFileType *gifFile = DGifOpenFileName(path, &err);

    //刷新
    DGifSlurp(gifFile);

    //创建一个GifBean 对象 malloc
    GifBean *gifBean = (GifBean *) (malloc(sizeof(GifBean)));
    // 清空数据
    memset(gifBean, 0, sizeof(GifBean));

    //数据绑定
    gifFile->UserData = gifBean;

    // 初始化数组 确定数组长度
    gifBean->dealys = (int *) malloc(sizeof(int) * gifFile->ImageCount);
    memset(gifBean->dealys, 0, sizeof(int) * gifFile->ImageCount);
    //获取时间

    gifFile->UserData = gifBean;
    gifBean->current_frame = 0;
    gifBean->total_frame = gifFile->ImageCount;

    ExtensionBlock *ext;

    for (int i = 0; i < gifFile->ImageCount; ++i) {

        SavedImage frame = gifFile->SavedImages[i];

        //扩展块
        for (int j = 0; j < frame.ExtensionBlockCount; ++j) {
            if (frame.ExtensionBlocks[j].Function == GRAPHICS_EXT_FUNC_CODE) {//图形扩展块
                ext = &frame.ExtensionBlocks[j];
                break;
            }

        }
        if (ext) {
            int frame_delay = 10 * (ext->Bytes[1] | (ext->Bytes[2] << 8));
//                printf("time:%d", frame);
            gifBean->dealys[i] = frame_delay;
        }
    }

    env->ReleaseStringUTFChars(path_, path);
    return (jlong) gifFile;
}

//获取宽度
extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_gifplay_GifHandler_getWidth(JNIEnv *env, jobject instance, jlong ndkGif) {


    GifFileType *gifFileType = (GifFileType *) ndkGif;
    return gifFileType->SWidth;
}

//获取高度
extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_gifplay_GifHandler_getHeight(JNIEnv *env, jobject instance, jlong ndkGif) {

    GifFileType *gifFileType = (GifFileType *) ndkGif;
    return gifFileType->SHeight;
}

//绘制图片
extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_gifplay_GifHandler_updateFrame(JNIEnv *env, jobject instance, jlong ndkGif,
                                             jobject bitmap) {

    GifFileType *gifFileType = (GifFileType *) ndkGif;
    GifBean *gifBean = (GifBean *) gifFileType->UserData;

    AndroidBitmapInfo info;

    //入参 出参 对象
    AndroidBitmap_getInfo(env, bitmap, &info);

    //空的gif ---Bitmap
    void *pixels;
    AndroidBitmap_lockPixels(env, bitmap, &pixels);
    drawFrame(gifFileType, gifBean, info, pixels);
    gifBean->current_frame += 1;
    if (gifBean->current_frame >= gifBean->total_frame - 1) {
        gifBean->current_frame = 0;
    }

    AndroidBitmap_unlockPixels(env, bitmap);
    return gifBean->dealys[gifBean->current_frame];

}