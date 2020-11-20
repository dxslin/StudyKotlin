package com.slin.study.kotlin.ui.floatwin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityFloatWindowBinding
import com.slin.study.kotlin.ui.testlist.TestListFragment

class FloatWindowActivity : BaseActivity() {

    private lateinit var binding: ActivityFloatWindowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置标题
        intent?.extras?.getString(TestListFragment.INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_float_window)

        initView()

    }

    private fun initView() {
        binding.apply {
            btnFloatWin.setOnClickListener { createFloatWin() }
            btnCheckPermission.setOnClickListener { checkPermission() }
        }
    }

    private fun checkPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "请打开悬浮窗权限", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")))
        } else {
            Toast.makeText(this, "已打开悬浮窗权限", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility", "ObsoleteSdkInt")
    private fun createFloatWin() {
        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val view = LayoutInflater.from(this).inflate(R.layout.win_float_test, null)
        val btnFloat: Button = view.findViewById(R.id.btn_float)

        val tvClose: TextView = view.findViewById(R.id.tv_close);
        tvClose.setOnClickListener {
            Log.d(TAG, "createFloatWin: float button click")
            manager.removeView(view)
        }

        val params = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
//        params.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            //刘海屏延伸到刘海里面
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                params.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.M
        ) {
            params.type = WindowManager.LayoutParams.TYPE_TOAST
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        /**
         * 设置`FLAG_NOT_TOUCH_MODAL`，window里面控件可以获取事件，window外面也能正常获取事件，
         * 但是返回按键无法使用，需要同时设置FLAG_NOT_FOCUSABLE
         *
         * 设置`FLAG_NOT_FOCUSABLE`，无法使用输入框，但是同FLAG_NOT_TOUCH_MODAL返回键能正常使用，
         * 如果需要使用输入框，那么将此flag去掉，一定要在view上面添加关闭按钮
         *
         */
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE


        params.dimAmount = 0.0f
        params.width = 160
        params.height = 160
        //需要透明背景的话要设置这个format
        params.format = PixelFormat.TRANSPARENT

        //实现view跟随手指滑动
        btnFloat.setOnTouchListener(object : View.OnTouchListener {
            var lastX = 0
            var lastY = 0
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
//                Log.d(TAG,
//                    "createFloatWin: ${event.action} $x $y ${params.x} ${params.width} ${params.y} ${params.height}")
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = x
                        lastY = y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x += (x - lastX)
                        params.y += (y - lastY)
                        manager.updateViewLayout(view, params)
                        lastX = x
                        lastY = y
                    }
                    MotionEvent.ACTION_UP -> {

                    }
                }
                return true

            }

        })

        manager.addView(view, params)


    }


}