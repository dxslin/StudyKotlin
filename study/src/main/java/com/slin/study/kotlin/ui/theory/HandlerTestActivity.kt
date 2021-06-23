package com.slin.study.kotlin.ui.theory

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
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
                }, 200)
            }
            btnAddIdleHandler.setOnClickListener {
                addIdleHandler()
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