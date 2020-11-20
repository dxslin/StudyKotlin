package com.slin.study.kotlin.ui.jetpack.hlit

import javax.inject.Inject


/**
 * author: slin
 * date: 2020/11/20
 * description:
 *
 */
interface AnalyticsService {
    fun analytics(): String
}

class AnalyticsServiceImpl @Inject constructor() : AnalyticsService {
    override fun analytics(): String {
        return AnalyticsServiceImpl::class.simpleName!!
    }
}

class AnalyticsServiceOther @Inject constructor() : AnalyticsService {
    override fun analytics(): String {
        return AnalyticsServiceOther::class.simpleName!!
    }

}


