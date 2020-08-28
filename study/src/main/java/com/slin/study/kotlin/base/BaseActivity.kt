package com.slin.study.kotlin.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.slin.study.kotlin.util.ThemeHelper


/**
 * author: slin
 * date: 2020/7/6
 * description:
 *
 */
open class BaseActivity : AppCompatActivity() {

    protected val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        applyThemeResource()
        super.onCreate(savedInstanceState)
        setShowBackButton(false)
    }

    /**
     * 设置主题
     */
    protected open fun applyThemeResource() {
        ThemeHelper.applyTheme(this)
        ThemeHelper.applyNightMode()
    }

    /**
     * 是否显示返回按键
     */
    protected fun setShowBackButton(value: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }

    /**
     * 设置状态栏颜色，color为透明的时候为沉浸式
     */
    protected fun setStatusBarColor(@ColorInt color: Int) {
        //沉浸式
        window.statusBarColor = color
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
