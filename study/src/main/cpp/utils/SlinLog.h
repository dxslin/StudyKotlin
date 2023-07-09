//
// Created by slin on 2023/7/9.
//

#ifndef STUDYKOTLIN_SLINLOG_H
#define STUDYKOTLIN_SLINLOG_H

#include <android/log.h>

#define TAG "SlinLog"
#define LOG(priority, format, ...) __android_log_print(priority, TAG, "%s %s " format, __FILE_NAME__, __FUNCTION__, ##__VA_ARGS__)
#define LOGI(...) LOG(ANDROID_LOG_INFO, __VA_ARGS__)
#define LOGD(...) LOG(ANDROID_LOG_INFO, __VA_ARGS__)
#define LOGW(...) LOG(ANDROID_LOG_WARN, __VA_ARGS__)
#define LOGE(...) LOG(ANDROID_LOG_ERROR, __VA_ARGS__)


#endif //STUDYKOTLIN_SLINLOG_H
