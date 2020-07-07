package com.slin.study.kotlin.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.slin.study.kotlin.R


/**
 * author: slin
 * date: 2020/7/6
 * description:
 *
 */
open class BaseActivity : AppCompatActivity() {

    private val TAG: String? = BaseActivity::class.simpleName

    /**
     * 是否显示返回按键
     */
    var isShowBackButton: Boolean = false
        set(value) {
            field = value
            if (value) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_white_24)
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isShowBackButton = false

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> if (isShowBackButton) {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
