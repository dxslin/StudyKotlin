package com.slin.core.ext


/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */

/**
 * 将可为空的类型转化为不可为空的，可以通过[default]提供默认值，否则会报异常
 * @return 不为空的值
 */
inline fun <reified T : Any> T?.forceNotNull(default: () -> T = { requireNotNull(this) }): T {
    return this ?: default()
}
