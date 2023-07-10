package com.slin.study.kotlin.ui.natively.mmap

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.os.RemoteException
import com.slin.core.logger.logd
import com.slin.core.logger.loge
import java.security.InvalidParameterException

class MMapTestClient {

    var isConnected = false
    private lateinit var mContext: Context
    private var mBinder: IMMapServerInterface? = null

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            logd { "onServiceConnected, name: $name  binder: $service" }
            isConnected = true
            mBinder = IMMapServerInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            logd { "onServiceDisconnected, name: $name" }
            isConnected = false
        }

        override fun onBindingDied(name: ComponentName?) {
            logd { "onBindingDied, name: $name" }
            super.onBindingDied(name)
            isConnected = false
            mBinder = null
        }
    }

    fun init(context: Context) {
        mContext = context
        context.bindService(
            Intent("com.slin.study.kotlin.ui.natively.mmap.MMapServerService.Bind").apply {
                `package` = context.packageName
            },
            conn,
            Context.BIND_AUTO_CREATE
        )
    }

    @Throws(RemoteException::class)
    fun passShm(fd: ParcelFileDescriptor?, size: Int){
        if(fd == null){
            loge { "fd cannot be null" }
            throw InvalidParameterException("fd cannot be null")
        }
        if (isConnected && mBinder != null) {
            mBinder?.passShm(fd, size)
        } else {
            loge { "Not connected: isConnected = $isConnected mBinder = $mBinder" }
        }
    }

    @Throws(RemoteException::class)
    fun writeText(text: String?) {
        if (isConnected && mBinder != null) {
            mBinder?.writeText(text)
        } else {
            loge { "Not connected: isConnected = $isConnected mBinder = $mBinder" }
        }
    }

    @Throws(RemoteException::class)
    fun readText(): String {
        if (isConnected && mBinder != null) {
            return mBinder?.readText() ?: ""
        } else {
            loge { "Not connected: isConnected = $isConnected mBinder = $mBinder" }
        }
        return "";
    }

}