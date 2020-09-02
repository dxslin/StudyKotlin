package com.slin.study.kotlin.ui.librarycase.kodein

import org.kodein.di.Kodein
import org.kodein.di.android.ActivityRetainedScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


/**
 * author: slin
 * date: 2020/9/1
 * description:
 *
 */

private const val MODULE_NAME_USER = "user_module"
val moduleUser = Kodein.Module(MODULE_NAME_USER) {
    bind<User>() with singleton {
        User.instance
    }
}


private const val MODULE_NAME_KODEIN_CASE_FRAGMENT = "module_name_kodein_case_fragment"
val moduleKodeinCase = Kodein.Module(MODULE_NAME_KODEIN_CASE_FRAGMENT) {

    bind<KodeinCaseFragment>() with scoped(ActivityRetainedScope).singleton {
        KodeinCaseFragment()
    }

    bind<UserManager>() with scoped(ActivityRetainedScope).singleton {
        UserManager.instant
    }

}

