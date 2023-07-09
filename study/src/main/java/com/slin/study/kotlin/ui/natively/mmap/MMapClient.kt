package com.slin.study.kotlin.ui.natively.mmap

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.slin.core.logger.logd
import com.slin.core.logger.loge

class MMapClient {

    var isConnected = false
    private lateinit var mContext: Context
    private var mBinder: IMMapServerInterface? = null

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            logd { "onServiceConnected, name: $name" }
            isConnected = true
            mBinder = service as IMMapServerInterface
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
            Intent(context, MMapServerService::class.java),
            conn,
            Context.BIND_AUTO_CREATE
        )
    }

    @Throws(RemoteException::class)
    fun passShm(fd: Int, addr: LongArray, size: Int) {
        if (isConnected && mBinder != null) {
            mBinder?.passShm(fd, addr, size)
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