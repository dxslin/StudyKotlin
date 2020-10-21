package com.slin.git.api.bean

import com.slin.git.config.PAGING_REMOTE_FIRST_PAGE
import com.slin.git.config.PAGING_REMOTE_PAGE_SIZE


/**
 * author: slin
 * date: 2020/9/18
 * description: 分页数据加载
 *
 */

/**
 * 起始页码
 */
const val START_PAGE_NUM = PAGING_REMOTE_FIRST_PAGE

/**
 * 每页数量
 */
const val PER_PAGE_NUM = PAGING_REMOTE_PAGE_SIZE

open class Page(val page: Int = START_PAGE_NUM) {

    open fun next(): Page = Page(page + 1)

    open fun reset(): Page = Page(START_PAGE_NUM)

}

class NoArgPage(page: Int) : Page(page)

class OneArgPage<T>(page: Int = START_PAGE_NUM, val arg: T? = null) : Page(page) {

    override fun next(): OneArgPage<T> {
        return OneArgPage(page + 1, arg)
    }

    override fun reset(): OneArgPage<T> {
        return OneArgPage(START_PAGE_NUM, arg)
    }
}

class TwoArgPage<T, R>(page: Int = START_PAGE_NUM, val arg1: T, val arg2: R) : Page(page) {

    override fun next(): TwoArgPage<T, R> {
        return TwoArgPage(page + 1, arg1, arg2)
    }

    override fun reset(): TwoArgPage<T, R> {
        return TwoArgPage(START_PAGE_NUM, arg1, arg2)
    }
}
