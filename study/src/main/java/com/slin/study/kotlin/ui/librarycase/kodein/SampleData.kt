package com.slin.study.kotlin.ui.librarycase.kodein


/**
 * author: slin
 * date: 2020/9/1
 * description:
 * 创建两个单例用于测试kodein的依赖注入
 */
class User private constructor(val name: String) {

    private object SingletonHolder {
        val holder = User("user slin")
    }

    companion object {
        val instance = SingletonHolder.holder
    }
}


class UserManager private constructor() {

    private object SingletonHolder {
        val holder = UserManager()
    }

    companion object {
        val instant = SingletonHolder.holder
    }

    fun getName() = "UserManager"

}
