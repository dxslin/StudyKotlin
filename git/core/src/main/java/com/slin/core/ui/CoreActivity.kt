package com.slin.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.*
import org.kodein.di.android.di
import org.kodein.di.android.retainedSubDI


/**
 * author: slin
 * date: 2020/8/28
 * description: BaseActivity
 *
 */
abstract class CoreActivity : AppCompatActivity(), DIAware {

    override val di: DI by retainedSubDI(di(), copy = Copy.All) {}

    override val diContext: DIContext<CoreActivity> = diContext { this }

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
