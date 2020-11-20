package com.slin.study.kotlin.ui.jetpack

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * author: slin
 * date: 2020/11/20
 * description:
 *
 *
 */
class HiltInjectViewModel @ViewModelInject constructor(
//    @AnalyticsServiceImplAnnotation
//    private val analyticsService: AnalyticsServiceImpl,
//    @AnalyticsServiceOtherAnnotation
//    private val analyticsServiceOther: AnalyticsService,
) : ViewModel() {

    private val _result: MutableLiveData<String> = MutableLiveData("result")
    val result: LiveData<String> = _result

//    @Inject
//    @AnalyticsServiceImplAnnotation
//    lateinit var analyticsService: AnalyticsService
//
//    @Inject
//    @AnalyticsServiceOtherAnnotation
//    lateinit var analyticsServiceOther: AnalyticsService

    fun analyticsServiceResult() {
//        _result.postValue(analyticsService.analytics())
    }

    fun analyticsServiceOtherResult() {
//        _result.postValue(analyticsServiceOther.analytics())
    }

}