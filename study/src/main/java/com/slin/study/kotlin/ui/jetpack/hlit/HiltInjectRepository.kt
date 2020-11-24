package com.slin.study.kotlin.ui.jetpack.hlit

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


/**
 * author: slin
 * date: 2020/11/24
 * description:
 *
 */

@ActivityRetainedScoped
class HiltInjectRepository @Inject constructor(
    @AnalyticsServiceImplAnnotation
    private val analyticsServiceImpl: AnalyticsService,
    @AnalyticsServiceOtherAnnotation
    private val analyticsServiceOther: AnalyticsService,
) {

    fun requestUserInfo(): String {
        return "$this"
    }

    fun analyticsServiceResult(): String {
        return analyticsServiceImpl.analytics()
    }

    fun analyticsServiceOtherResult(): String {
        return analyticsServiceOther.analytics()
    }
}