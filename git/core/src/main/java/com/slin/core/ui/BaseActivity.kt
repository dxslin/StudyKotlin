package com.slin.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * author: slin
 * date: 2020/8/28
 * description: BaseActivity
 *
 */
open abstract class BaseActivity : AppCompatActivity() {

    /**
     * 布局文件id
     */
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutResId > 0) {
            setContentView(layoutResId)
            initView()
        }
    }

    protected open fun initView() {

    }

}
