//
// Created by slin on 2023/7/9.
//

#include "FileUtils.h"
#include "SlinLog.h"

namespace SlinUtils {

    /**
     * 递归创建目录
     */
    int FileUtils::mkdirs(const string &path, int mode) {
        if (access(path.c_str(), F_OK) == 0) {
            return 0;
        }
        unsigned long size = path.size();
        char subDir[size + 1];
        memset(subDir, 0, size);
        int ret = 0;
        for (int i = 0; i < size; ++i) {
            subDir[i] = path.at(i);
            if (path.at(i) == '/' || path.at(i) == '\\') {
//                LOGI("mkdirs i: %d dir: %s", i, subDir);
                if (access(subDir, F_OK) != 0) {
                    ret = mkdir(subDir, mode);
                    if (ret != 0) {
                        LOGI("mkdirs filed: %s, err: %s", subDir, strerror(ret));
                        return ret;
                    }
                }
            }
        }
        return 0;
    }

} // SlinUtils