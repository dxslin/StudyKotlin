#include <jni.h>
#include <android/file_descriptor_jni.h>
#include "shm/SharedMemory.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "UnusedParameter"
//
// Created by slin on 2023/7/9.
//
SharedMemory shm = SharedMemory();

extern "C"
JNIEXPORT void JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_MMapTestActivity_nativeOpenShm(JNIEnv *env,
                                                                           jobject thiz,
                                                                           jstring file_path) {
    const char *filePath = env->GetStringUTFChars(file_path, nullptr);
    int ret = shm.openShm(filePath);
    if (ret != 0) {
        LOGE("Open Shared Memory Failed.");
    }
    LOGI("Open Shared Memory Success.");
    env->ReleaseStringUTFChars(file_path, filePath);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_MMapTestActivity_nativeWriteText(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jstring text) {
    const char *wText = env->GetStringUTFChars(text, nullptr);
    int ret = shm.writeShm(wText, strlen(wText) + 1);
    if (ret != 0) {
        LOGE("Write Shared Memory Failed.");
    }
    LOGI("Write Shared Memory Success.");
    shm.flushShm();
    env->ReleaseStringUTFChars(text, wText);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_MMapTestActivity_nativeReadText(JNIEnv *env,
                                                                            jobject thiz) {
    int size = shm.getSize();
    char data[size];
    memset(data, 0, size);
    int ret = shm.readShm((void *) data, size);
    if (ret != 0) {
        LOGE("Read Shared Memory Failed.");
    }
    LOGI("Read Shared Memory Success.");
    return env->NewStringUTF(data);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_MMapTestActivity_nativeCloseShm(JNIEnv *env,
                                                                            jobject thiz) {
    shm.closeShm();
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_MMapTestActivity_nativeGetShareMemory(JNIEnv *env,
                                                                                  jobject thiz) {
    jclass shmCls = env->FindClass("com/slin/study/kotlin/ui/natively/mmap/ShareMemory");
    jmethodID shmCMI = env->GetMethodID(shmCls, "<init>", "(Landroid/os/ParcelFileDescriptor;I)V");

    jclass pfdCls = env->FindClass("android/os/ParcelFileDescriptor");
    jmethodID pfdFromFd = env->GetStaticMethodID(pfdCls, "fromFd", "(I)Landroid/os/ParcelFileDescriptor;");
    jobject fd = env->CallStaticObjectMethod(pfdCls, pfdFromFd, shm.getFd());

    return env->NewObject(shmCls, shmCMI, fd, shm.getSize());
}
#pragma clang diagnostic pop