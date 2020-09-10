package com.slin.core.di


/**
 * author: slin
 * date: 2020/9/3
 * description: 创建一个带参数的单例，双重锁模式
 * See https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */

/**
 * 创建一个单参数构造函数的单例
 */
open class SingletonHolderSingleArg<out T, in A>(private val creator: (arg: A) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T =
        instance ?: synchronized(this) {
            instance ?: creator(arg).apply {
                instance = this
            }
        }

}

/**
 * 创建一个双参数构造函数的单例
 */
open class SingletonHolderDoubleArgs<out T, in A1, in A2>(private val creator: (arg1: A1, arg2: A2) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg1: A1, arg2: A2): T =
        instance ?: synchronized(this) {
            instance ?: creator(arg1, arg2).apply {
                instance = this
            }
        }

}
