#include <jni.h>
#include "shm/SharedMemory.h"

SharedMemory serverShm;

extern "C"
JNIEXPORT void JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_ServerBinderImpl_nativePassShm(JNIEnv *env,
                                                                           jobject thiz, jobject pfd,
                                                                           jint size) {
    jclass  pfdCls = env->GetObjectClass(pfd);
    jmethodID pfdGetFd = env->GetMethodID(pfdCls, "getFd", "()I");
    int fd = env->CallIntMethod(pfd, pfdGetFd);
    LOGI("fd: %d size: %d ", fd, size);
    int ret = serverShm.openShm(fd, size);
    if (ret != 0) {
        LOGE("Open shared memory failed.");
    }
    LOGI("Open shared memory success.");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_ServerBinderImpl_nativeWriteText(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jstring text) {
    const char *txt = env->GetStringUTFChars(text, nullptr);
    size_t size = strlen(txt) + 1;
    int ret = serverShm.writeShm(txt, size);
    if (ret != 0) {
        LOGE("Write data failed.");
    }
    LOGI("Write data success.");
    env->ReleaseStringUTFChars(text, txt);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_slin_study_kotlin_ui_natively_mmap_ServerBinderImpl_nativeReadText(JNIEnv *env,
                                                                            jobject thiz) {
    int size = serverShm.getSize();
    char *data[size];
    memset(data, 0, size);
    int ret = serverShm.readShm(data, size);
    if (ret != 0) {
        LOGE("Read data failed.");
    }
    const char *str = reinterpret_cast<const char *>(data);
    LOGI("Read data success. data: %s", str);
    return env->NewStringUTF(str);
}