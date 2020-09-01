package com.slin.study.kotlin.ui.librarycase.kodein

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton


/**
 * author: slin
 * date: 2020/9/1
 * description:
 *
 */

private const val USER_MODULE_NAME = "user_module"
val userModule = Kodein.Module(USER_MODULE_NAME) {
    bind<User>() with singleton {
        User.instance
    }

    bind<UserManager>() with singleton {
        UserManager.instant
    }
}