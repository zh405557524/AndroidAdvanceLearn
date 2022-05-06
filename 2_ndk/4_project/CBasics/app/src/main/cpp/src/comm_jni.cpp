//
// Created by tech2 on 2022/1/21.
//

#include <jni.h>
#include <string>
#include "log.h"

#define  MAX 10 //声明一个常量，叫MAX ，值是10 ，常量一旦初始化不可以改变

/**
 * 一、数据类型
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_soul_cbasics_MainActivity_data_1type(JNIEnv *env, jobject thiz) {

    //1、变量/常量定义
    {
        int a;//定义了一个变量，其类型

        const int b = 10;//定义了有一个const 常量，名叫b，值为10。
        //b =11;//err ,常量的值不能改变
        //MAX= 100;//err 常量的值不能改变
        int abc = MAX;//将adb的值位置为MAX的值。
        a = 123;
        printf("chat:%d", a);
        LOGI("data_1type:%d\n", a)
    }

    //2、进制
    {
        int a = 123;//十进制方式赋值
        int b = 0123;//八进制赋值
        int c = 0xABC;//十六进制方式赋值
        //如果在printf中输出一个十进制数那么用%d，八进制用%o，十六进制是%x
        LOGI("十进制：%d", a)
        LOGI("八进制：%o", b)
        LOGI("十六进制：%x\n", c)
    }

    //3、补码
    {
        int a = -15;
        LOGI("补码：%x\n", a)
    }

    //4、sizeof关键字
    {
        int a = 134432143;
        int b = sizeof(a);//sizeof得到指定值占用内存的大小，单位：字节
        LOGI("a=：%x", b)
        size_t c = sizeof(a);
        LOGI("c=：%u\n", c);//用无符合数的方式输出c的值
    }

    //5、变量输出定义
    {
        //5.1 整形
        //%d  输出有符号的10进制int 类型 ;   %o 输出8禁止的int类型 ; %x 输出16进制的int 类型，以小写输出;
        // %X  输出16进制的int 类型，以大写输出;%u 输出一个10进制的无符合数
//        int a = 123;//定义变量a，以10进制方式复制为 123
//        int b = 0567;//定义变量b，以8进制方式赋值为0576
//        int c = 0xabc;//定义变量c，以16进制方式赋值为0xabc
//
//        LOGI("a=%d", a);
//        LOGI("b=%o", b);
//        LOGI("c=%x", c);
//        LOGI("c=%X", c);
//        LOGI("c=%d", c);
//
//        unsigned int d = 0xffffff;//定义无符合int 变量d,以16进制方式赋值
//        LOGI("有符合方式打印：d=%d", d);
//        LOGI("无符合方式打印：d=%u", d);

        //5.2 其他数据类型  short、int、log、long long
        //内存占用大小: short 2个字节;int 4个字节; long win4字节 lux 4字节(32位) 8字节(64位) ;long long 8字节

        // 10 int 类型; 10l,10L 代表long类型; 10ll 10LL 代表long long 类型; 10u 10U 代表unsigned int 类型
        // 10ul,10Ul 代表unsigned long 类型; 10ull,10ULL 代表 unsigned long long 类型

        //输出格式  %hd 输出short类型；%d 输出int类型; %l 输出long类型 ; %ll 输出long long类型 ;%hu 出书 unsigned short类型
        //          %u 输出unsigned int类型; %lu 输出unsigned long 类型 ; %llu 输出unsigned long long 类型

        short a = 10;
        int b = 10;
        long c = 10l;
        long long d = 10ll;
        LOGI("sizeof(a)=%u", sizeof(a));
        LOGI("sizeof(b)=%u", sizeof(b));
        LOGI("sizeof(c)=%u", sizeof(c));
        LOGI("sizeof(d)=%u", sizeof(d));

        LOGI("short a= %hd",a);
        LOGI("int b= %d",b);
        LOGI("int c= %ld",c);
        LOGI("int d= %lld",d);



    }

}

