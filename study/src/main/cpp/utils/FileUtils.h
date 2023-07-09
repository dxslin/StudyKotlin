//
// Created by slin on 2023/7/9.
//

#ifndef STUDYKOTLIN_FILEUTILS_H
#define STUDYKOTLIN_FILEUTILS_H

#include <string>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

using namespace std;

namespace SlinUtils {

    class FileUtils {
    public:
        static int mkdirs(const string &path, int mode);
    };

} // SlinUtils

#endif //STUDYKOTLIN_FILEUTILS_H
