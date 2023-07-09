package com.slin.study.kotlin.ui.natively.mmap

class ServerBinderImpl : IMMapServerInterface.Stub() {

    companion object{
        init {
            System.loadLibrary("native-mmap-server")
        }
    }

    override fun passShm(fd: Int, addr: LongArray, size: Int) {
        nativePassShm(fd, addr, size)
    }

    override fun writeText(text: String?) {
        nativeWriteText(text)
    }

    override fun readText(): String {
        return nativeReadText()
    }

    private external fun nativePassShm(fd: Int, addr: LongArray, size: Int)
    private external fun nativeWriteText(text: String?)
    private external fun nativeReadText(): String
}
