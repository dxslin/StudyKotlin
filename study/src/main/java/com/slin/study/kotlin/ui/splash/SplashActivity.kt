package com.slin.study.kotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.slin.study.kotlin.MainActivity
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivitySplashBinding

/**
 * SplashActivity
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/2/10
 */

/**
 * 倒计时总时间
 */
const val COUNT_DOWN_TIME = 1000L

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun applyThemeResource() {
        setTheme(R.style.AppTheme_Splash)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.hide()
//        setStatusBarColor(ContextCompat.getColor(this, R.color.white))

        val countDownTimer = object : CountDownTimer(COUNT_DOWN_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvSkipTimer.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                startMainActivity()
            }
        }
        countDownTimer.start()

        binding.tvSkipTimer.setOnClickListener {
            startMainActivity()
            countDownTimer.cancel()
        }

    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}