package com.slin.git.utils.agent

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method


/**
 * author: slin
 * date: 2020/10/16
 * description:
 *
 */
class SAgent<T : Any>(obj: T, methodNames: List<String>) : InvocationHandler {

    @Override
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {


        TODO("Not yet implemented")
    }


}

interface MethodInterceptor<T : Any> {

    fun onBefore(obj: T, method: Method)

    fun onAfter(obj: T, method: Method)

}