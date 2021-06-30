package com.slin.study.kotlin.ui.theory

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.SystemClock
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityHandlerTestBinding
import com.slin.study.kotlin.util.Logger

/**
 * author: slin
 * date: 2021/6/23
 * description:
 *
 */
class HandlerTestActivity : BaseActivity() {

    private lateinit var binding: ActivityHandlerTestBinding

    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setShowBackButton(true)
        title = "HandlerTest"
        initView()

    }

    private fun initView() {
        handlerThread = HandlerThread("slin_test_idle")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        handlerThread.looper.setMessageLogging {
            Logger.log(TAG, "logging $it")
        }

        binding.apply {
            btnPostMessage.setOnClickListener {
                Logger.log(TAG, "post message ")
                handler.post {
                    Logger.log(TAG, "post message execute")
                }
            }
            btnPostDelayMessage.setOnClickListener {
                Logger.log(TAG, "post delay message ")
                handler.postDelayed({
                    Logger.log(TAG, "post delay message execute")
                }, 60_000)
            }
            btnAddIdleHandler.setOnClickListener {
                addIdleHandler()
            }
            btnGetTime.setOnClickListener {
                // 系统时间，修改系统设置时，时间可能会被修改
                Logger.log(TAG, "System.currentTimeMillis: \t\t\t\t${System.currentTimeMillis()}")
                // 系统启动之后的运行时间，深度睡眠不计入
                Logger.log(TAG, "SystemClock.uptimeMillis: \t\t\t\t${SystemClock.uptimeMillis()}")
                // 系统启动之后的运行时间，包括深度睡眠时
                Logger.log(
                    TAG,
                    "SystemClock.elapsedRealtime: \t\t\t${SystemClock.elapsedRealtime()}"
                )
                // 当前线程运行时间
                Logger.log(
                    TAG,
                    "SystemClock.currentThreadTimeMillis: \t${SystemClock.currentThreadTimeMillis()}"
                )

            }
        }

    }

    private fun addIdleHandler() {
        handlerThread.looper.queue.addIdleHandler {
            Logger.log(TAG, "idle handler execute")
            false
        }
    }

}