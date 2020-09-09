package com.slin.git.stroage.local

import android.content.SharedPreferences
import com.slin.core.ext.boolean
import com.slin.core.ext.string
import com.slin.core.utils.SingletonHolderSingleArg


/**
 * author: slin
 * date: 2020/9/3
 * description: 缓存的github账户信息
 *
 */
class GitUserInfoStorage(prefs: SharedPreferences) {

    var username by prefs.string("username", "")

    var password by prefs.string("password", "")

    var token by prefs.string("token", "")

    var isAutoLogin by prefs.boolean("isAutoLogin", true)

    companion object :
            SingletonHolderSingleArg<GitUserInfoStorage, SharedPreferences>(::GitUserInfoStorage)

}

