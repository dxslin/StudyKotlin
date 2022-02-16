package com.slin.study.kotlin.ui.floatwin

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.slin.core.logger.logd
import com.slin.study.kotlin.R
import com.slin.study.kotlin.StudyKotlinApplication
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityFloatWindowBinding
import com.slin.study.kotlin.ui.jetpack.HiltInjectActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.zj.easyfloat.EasyFloat

class FloatWindowActivity : BaseActivity() {

    private lateinit var binding: ActivityFloatWindowBinding
    private val displayMetrics: DisplayMetrics = DisplayMetrics()

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display?.getRealMetrics(displayMetrics)
        } else {
            window?.windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
        }
        binding.apply {
            btnFloatWin.setOnClickListener { createFloatWin() }
            btnCheckPermission.setOnClickListener { checkPermission() }

            btnFloatWinNoPermission.setOnClickListener { easyFloat() }

            btnCustomToast.setOnClickListener { showCustomToast() }
        }
    }

    private fun checkPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "请打开悬浮窗权限", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
            )
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

        val params = WindowManager.LayoutParams()
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

        params.gravity = Gravity.START.and(Gravity.TOP)

        params.dimAmount = 0.0f
        params.width = 160
        params.height = 160
        //需要透明背景的话要设置这个format
        params.format = PixelFormat.TRANSPARENT

        /**
         * 这里发现x=0；y=0竟然是屏幕中心，但是FrameDecorator里面却是左上角，有点奇怪
         */
        params.x = displayMetrics.widthPixels / 2
        params.y = 0

        //实现view跟随手指滑动
        btnFloat.setOnTouchListener(object : View.OnTouchListener {
            var lastX = 0
            var lastY = 0
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                logd { "onTouch: ${event.action} $x $y $lastX $lastY ${event.x} ${event.y} ${params.x} ${params.y}" }
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
                        val holder = PropertyValuesHolder.ofInt(
                            "trans",
                            params.x,
                            if (params.x > 0) displayMetrics.widthPixels / 2 else -displayMetrics.widthPixels / 2
                        )
                        val valueAnimator = ValueAnimator.ofPropertyValuesHolder(holder)
                        valueAnimator.addUpdateListener {
                            params.x = it.getAnimatedValue("trans") as Int
                            manager.updateViewLayout(view, params)
                        }
                        valueAnimator.interpolator = AccelerateInterpolator()
                        valueAnimator.duration = 180
                        valueAnimator.start()
                    }
                }
                return true

            }

        })

        manager.addView(view, params)


    }

    private fun easyFloat() {
        EasyFloat
            .layout(R.layout.layout_float_view)
            .blackList(mutableListOf(HiltInjectActivity::class.java))
            .layoutParams(initLayoutParams())
            .setAutoMoveToEdge(false)
            .dragEnable(false)
            .listener {
                it.findViewById<TextView>(R.id.tv_title).setOnClickListener {
                    EasyFloat.dismiss(this);
                }
                it.findViewById<TextView>(R.id.tv_content).setOnClickListener {
                    Toast.makeText(this, "Easy Float Window Content", Toast.LENGTH_SHORT).show()
                    Log.d("Slin", "Easy Float Window content click")
                }
            }
            .show(this)
    }

    private fun initLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP
        return params
    }

    private fun showCustomToast() {
        val toast = Toast(StudyKotlinApplication.INSTANCE);
        val view = layoutInflater.inflate(R.layout.layout_float_view, null)
        view.findViewById<TextView>(R.id.tv_title).setOnClickListener { toast.cancel() }
        view.findViewById<TextView>(R.id.tv_content).setOnClickListener {
            Toast.makeText(this, "content", Toast.LENGTH_SHORT).show()
            Log.d("Slin", "Toast content click")
        }

        toast.view = view;
        toast.duration = Toast.LENGTH_LONG;
        toast.setGravity(Gravity.TOP, 0, 20)
        toast.show()
    }


}