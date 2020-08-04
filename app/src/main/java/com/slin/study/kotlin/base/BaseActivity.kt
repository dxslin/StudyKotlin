package com.slin.study.kotlin.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


/**
 * author: slin
 * date: 2020/7/6
 * description:
 *
 */
open class BaseActivity : AppCompatActivity() {

    private val TAG: String? = BaseActivity::class.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setShowBackButton(false)
    }

    /**
     * 是否显示返回按键
     */
    protected fun setShowBackButton(value: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
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
