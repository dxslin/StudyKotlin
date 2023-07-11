//
// Created by slin on 2023/7/9.
//

#include "SharedMemory.h"

SharedMemory::SharedMemory() {
    LOGI("SharedMemory");
}

SharedMemory::~SharedMemory() {
    closeShm();
}

int SharedMemory::openShm(const int fd, const int size) {
    mFd = fd;
    mSize = size;
    return createMMAP();
}

int SharedMemory::openShm(const char *filePath, const int size) {
    mSize = size;
    int ret = openFile(filePath);
    if (ret < 0) {
        LOGE("Open file failed.");
        return ret;
    }
    return createMMAP();
}

int SharedMemory::openFile(const char *filePath) {
    if (access(filePath, F_OK) != 0) {
        int ret = FileUtils::mkdirs(filePath, S_IRWXU | S_IRWXG | S_IRWXO);
        if (ret != 0) {
            LOGE("Create directory(%s) failed: %s", filePath, strerror(ret));
            return ret;
        }
    }
    int fd = open(filePath, O_RDWR | O_CREAT, S_IRWXU | S_IRWXG | S_IRWXO);
    if (fd < 0) {
        LOGE("Open file(%s) failed.", filePath);
        return -1;
    }
    mFd = fd;
    return 0;
}

int SharedMemory::createMMAP() {
    ftruncate(mFd, mSize);
    void *address = mmap(nullptr, mSize, PROT_READ | PROT_WRITE, MAP_SHARED, mFd, 0);
    LOGI("mmap addr: %p mAddress: %p", address, mAddress);
    if (address == (void *) -1) {
        LOGE("mmap failed. err: %s", strerror(errno));
        return -1;
    }
    mAddress = address;
    return 0;
}

int SharedMemory::writeShm(const void *data, const size_t size) const {
    if (size > mSize) {
        LOGE("Write data limits %d bytes, but try writing %zu bytes", mSize, size);
        return -1;
    }
    // write fd
//    size_t cnt = write(mFd, data, size);
//    if (cnt != size) {
//        LOGE("Only write %ld bytes data, not %d", cnt, size);
//        return -1;
//    }
    memcpy(mAddress, data, size);
    return 0;
}

int SharedMemory::readShm(void *outData, const int size) const {
    if (size > mSize) {
        LOGE("Read data limits %d bytes, but try reading %d bytes", mSize, size);
        return -1;
    }
//    size_t cnt = read(mFd, outData, size);
//    if (cnt != size) {
//        LOGE("Read %ld bytes data, not %d", cnt, size);
//        return -1;
//    }
    memcpy(outData, mAddress, size);
    return 0;
}

void SharedMemory::flushShm() const {
    if (msync(mAddress, mSize, MS_SYNC) != 0) {
        LOGE("msync failed.");
    }
}

__attribute__((unused)) const void *SharedMemory::getAddress() const {
    return mAddress;
}

void SharedMemory::closeShm() const {
    if (munmap(mAddress, mSize) != 0) {
        LOGE("munmap failed.");
    }
    if (mFd > 0) {
        close(mFd);
    }
}

int SharedMemory::getSize() const {
    return mSize;
}

int SharedMemory::getFd() const {
    return mFd;
}
