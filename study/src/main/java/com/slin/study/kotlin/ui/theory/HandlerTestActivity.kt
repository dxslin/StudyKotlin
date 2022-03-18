package com.slin.study.kotlin.ui.theory

import android.os.*
import android.view.Choreographer
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityHandlerTestBinding
import com.slin.study.kotlin.util.Logger

/**
 * author: slin
 * date: 2021/6/23
 * description:
 *
 */

const val MSG_WHAT_HELLO = 1

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
        handler = object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_WHAT_HELLO -> Logger.log(TAG, "handleMessage: $msg")
                }

            }

        }
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
                }, 20_000)
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

            btnOtherHandler.setOnClickListener {
                // 不同的Handler使用同一个Looper，发送消息，其他Handler是收不到消息的，但是消息执行完毕之后IdleHandler会被执行
                Logger.log(TAG, "New Handler sendEmptyMessage")
                Handler(handlerThread.looper).sendEmptyMessage(MSG_WHAT_HELLO)

            }

            btnChoreographer.setOnClickListener {
                Choreographer.getInstance().postFrameCallback {
                    Logger.log(TAG, "Choreographer execute")
                }


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