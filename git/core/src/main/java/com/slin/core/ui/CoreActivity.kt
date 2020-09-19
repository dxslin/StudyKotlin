package com.slin.core.ui

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
open class CoreActivity : AppCompatActivity(), DIAware {

    override val di: DI by retainedSubDI(di(), copy = Copy.All) {}

    override val diContext: DIContext<CoreActivity> = diContext { this }


}
