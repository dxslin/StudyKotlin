package com.slin.core.net

/**
 * author: slin
 * date: 2020-09-07
 * description: 网络错误，其中错误码最好需要自己配置和扩展
 *
 */
sealed class Errors : Throwable() {

    companion object {
        /**
         * 输入数据为空
         */
        val EMPTY_INPUT_CODE = -100

        /**
         * 输出数据为空
         */
        val EMPTY_RESULT_CODE = -101
    }

    /**
     * 输出错误
     */
    data class DataError(val code: Int = -1, val errorMsg: String = "") : Errors()

    /**
     * IO异常
     */
    data class IOError(val throwable: Throwable) : Errors()

    /**
     * 没有网络连接错误
     */
    object NoNetWorkError : Errors()


}
