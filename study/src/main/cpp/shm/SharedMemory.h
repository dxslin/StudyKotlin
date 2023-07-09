//
// Created by slin on 2023/7/9.
//

#ifndef STUDYKOTLIN_SHAREDMEMORY_H
#define STUDYKOTLIN_SHAREDMEMORY_H

#include <unistd.h>
#include <fcntl.h>
#include <sys/mman.h>
#include "utils/FileUtils.h"
#include "utils/SlinLog.h"

#define DEFAULT_SIZE 128

using namespace SlinUtils;

class SharedMemory {

public:
    SharedMemory();

    ~SharedMemory();

    int openShm(const char *filePath, const int size = DEFAULT_SIZE);

    int openShm(const int fd, void *addr, const int size = DEFAULT_SIZE);

    int writeShm(const void *data, const size_t size) const;

    int readShm(void *outData, const int size) const;

    void flushShm() const;

    void closeShm() const;

    const void *getAddress() const;

    int getSize() const;

    int getFd() const;

private:
    int openFile(const char *filePath);

    int createMMAP(void *addr = nullptr);

private:
    int mSize = DEFAULT_SIZE;
    int mFd = 0;
    void *mAddress = nullptr;
};


#endif //STUDYKOTLIN_SHAREDMEMORY_H
