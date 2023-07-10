package com.slin.study.kotlin.ui.natively.mmap

import android.os.ParcelFileDescriptor
import com.slin.core.logger.loge
import java.security.InvalidParameterException

class ServerBinderImpl : IMMapServerInterface.Stub() {

    companion object{
        init {
            System.loadLibrary("native-mmap-server")
        }
    }

    override fun passShm(fd: ParcelFileDescriptor?, size: Int) {
        if(fd == null){
            throw InvalidParameterException("fd cannot be null")
        }
        nativePassShm(fd, size)
    }

    override fun writeText(text: String?) {
        nativeWriteText(text)
    }

    override fun readText(): String {
        return nativeReadText()
    }

    private external fun nativePassShm(fd: ParcelFileDescriptor, size: Int)
    private external fun nativeWriteText(text: String?)
    private external fun nativeReadText(): String
}
