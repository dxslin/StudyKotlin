package com.slin.study.kotlin.ui.natively.mmap

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.slin.core.logger.logd

class MMapServerService : Service() {

    private val binder by lazy { ServerBinderImpl() }

    override fun onBind(intent: Intent?): IBinder {
        logd { "onBind" }
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        logd { "onCreate" }
    }

}