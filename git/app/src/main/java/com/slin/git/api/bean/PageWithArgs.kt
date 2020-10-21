package com.slin.git.api.bean


/**
 * author: slin
 * date: 2020/9/18
 * description: 分页数据加载
 *
 */

sealed class Page

data class NoArgPage(val page: Int) : Page()

data class OneArgPage<T>(val page: Int = 0, val args: T? = null) : Page() {

    fun nextPage(): OneArgPage<T> {
        return this.copy(page = page.inc())
    }

    fun resetPage(): OneArgPage<T> {
        return this.copy(page = 0)
    }
}

data class TwoArgPage<T, R>(val page: Int, val arg1: T, val arg2: R) : Page() {

    fun nextPage(): TwoArgPage<T, R> {
        return this.copy(page = page.inc())
    }
}
