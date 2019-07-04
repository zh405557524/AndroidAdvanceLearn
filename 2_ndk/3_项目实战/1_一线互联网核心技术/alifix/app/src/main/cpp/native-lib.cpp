//
// Created by hillliao on 2019-07-01.
//

#include <jni.h>
#include <string>
#include "art_method.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_soul_alifix_DexManger_replace(JNIEnv *env, jclass type, jobject wrongMethod,
                                       jobject rightMethod) {
    //ArtMethod android 系统源码中
    art::mirror::ArtMethod *wrong = reinterpret_cast<art::mirror::ArtMethod *>(env->FromReflectedMethod(
            wrongMethod));
    art::mirror::ArtMethod *right = reinterpret_cast<art::mirror::ArtMethod *>(env->FromReflectedMethod(
            rightMethod));

    wrong->declaring_class_ = right->declaring_class_;
    wrong->dex_cache_resolved_methods_ = right->dex_cache_resolved_methods_;

    wrong->access_flags_ = right->access_flags_;
    wrong->dex_cache_resolved_types_ = right->dex_cache_resolved_types_;
    wrong->dex_code_item_offset_ = right->dex_code_item_offset_;

    //这里 方法索引的替换
    wrong->method_index_ = right->method_index_;
    wrong->dex_method_index_ = right->dex_method_index_;


}