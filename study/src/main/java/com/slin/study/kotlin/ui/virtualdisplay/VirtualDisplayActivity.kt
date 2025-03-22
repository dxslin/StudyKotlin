package com.slin.study.kotlin.ui.virtualdisplay

import android.content.res.Configuration
import android.graphics.SurfaceTexture
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Surface
import com.slin.core.logger.logd
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.ReflectUtils

class VirtualDisplayActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virtual_display)
        setShowBackButton(true)

        title = "Virtual Display"

        val surfaceTexture = SurfaceTexture(false)
        val surface = Surface(surfaceTexture)
        val displayManager = getSystemService(DisplayManager::class.java)
        displayManager.createVirtualDisplay(
            "Slin Virtual Display",
            400,
            200,
            160,
            surface,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
        )

        displayManager.registerDisplayListener(object : DisplayManager.DisplayListener {
            override fun onDisplayAdded(displayId: Int) {
                logd { "onDisplayAdded: $" }
                val configuration = ReflectUtils.getFieldValue<Configuration>(
                    this@VirtualDisplayActivity,
                    "mCurrentConfig"
                )
                if (configuration != null) {
                    ReflectUtils.invokeMethod<Unit>(
                        this@VirtualDisplayActivity,
                        "dispatchMovedToDisplay",
                        displayId,
                        configuration
                    )
                }

            }

            override fun onDisplayRemoved(displayId: Int) {
                logd { "onDisplayRemoved: $" }
            }

            override fun onDisplayChanged(displayId: Int) {
                logd { "onDisplayChanged: $" }
            }
        }, Handler(Looper.getMainLooper()))
    }
}