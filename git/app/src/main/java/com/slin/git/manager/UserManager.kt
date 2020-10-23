package com.slin.git.manager

import androidx.lifecycle.MutableLiveData
import com.slin.core.ext.forceNotNull
import com.slin.git.api.entity.UserInfo

/**
 * author: slin
 * date: 2020-09-07
 * description: 用户信息缓存
 */

object UserManager {

    var isLoggedIn: Boolean = false
        private set

    val userInfo: MutableLiveData<UserInfo?> = MutableLiveData<UserInfo?>(null).apply {
        observeForever { value ->
            isLoggedIn = (value != null)
        }
    }

    fun requireUserName(): String {
        return userInfo.value?.name.forceNotNull {
            //todo async go to login
            ""
        }
    }

}

