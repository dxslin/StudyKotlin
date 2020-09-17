package com.slin.core.ui

import androidx.fragment.app.Fragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.x.closestDI
import org.kodein.di.diContext


/**
 * author: slin
 * date: 2020/8/28
 * description:
 *
 */

open class CoreFragment : Fragment(), DIAware {

    override val di: DI by closestDI()

    override val diContext: DIContext<Fragment> = diContext { this }


}
