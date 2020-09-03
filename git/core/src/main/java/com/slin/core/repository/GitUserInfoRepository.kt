package com.slin.core.repository

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
class GitUserInfoRepository(prefs: SharedPreferences) {

    val username by prefs.string("username", "")

    val password by prefs.string("password", "")

    val token by prefs.string("token", "")

    val isAutoLogin by prefs.boolean("isAutoLogin", true)

    companion object :
        SingletonHolderSingleArg<GitUserInfoRepository, SharedPreferences>(::GitUserInfoRepository)

}

