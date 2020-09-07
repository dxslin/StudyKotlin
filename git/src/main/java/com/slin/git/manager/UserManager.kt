package com.slin.git.manager

import com.slin.git.entity.UserInfo

/**
 * author: slin
 * date: 2020-09-07
 * description: 用户信息缓存
 */

object UserManager {
    lateinit var INSTANCE: UserInfo
    var isLoggedIn: Boolean = false

}
