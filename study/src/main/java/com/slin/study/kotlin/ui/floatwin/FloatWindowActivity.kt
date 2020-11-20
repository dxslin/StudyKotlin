package com.slin.study.kotlin.ui.floatwin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityFloatWindowBinding

class FloatWindowActivity : BaseActivity() {

    private lateinit var binding: ActivityFloatWindowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        } else {
            Toast.makeText(this, "已打开悬浮窗权限", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createFloatWin() {
        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val view = LayoutInflater.from(this).inflate(R.layout.win_float_test, null)
        val btnFloat: Button = view.findViewById(R.id.btn_float)
        btnFloat.setOnClickListener {
            Log.d(TAG, "createFloatWin: float button click")
            manager.removeView(view)
        }

        val params = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
//        params.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
        }
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        params.dimAmount = 0.0f
        params.width = 160
        params.height = 160

        btnFloat.setOnTouchListener(object : View.OnTouchListener {
            var lastX = 0
            var lastY = 0
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                Log.d(TAG,
                    "createFloatWin: ${event.action} $x $y ${params.x} ${params.width} ${params.y} ${params.height}")
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