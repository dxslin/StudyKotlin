package com.slin.git.api.bean


/**
 * author: slin
 * date: 2020/9/18
 * description: 分页数据加载
 *
 */

data class Page(var page: Int = 0)

data class PageWithArgs<T>(val page: Int = 0, val args: T? = null)