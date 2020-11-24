package com.slin.study.kotlin.ui.jetpack

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slin.study.kotlin.ui.jetpack.hlit.HiltInjectRepository


/**
 * author: slin
 * date: 2020/11/20
 * description:
 *
 *
 */
class HiltInjectViewModel @ViewModelInject constructor(
    private val repository: HiltInjectRepository,
) : ViewModel() {

    private val _result: MutableLiveData<String> = MutableLiveData("result")
    val result: LiveData<String> = _result


    fun requestUserInfo() {
        _result.postValue(repository.requestUserInfo())
    }

    fun analyticsServiceResult() {
        _result.postValue(repository.analyticsServiceResult())
    }

    fun analyticsServiceOtherResult() {
        _result.postValue(repository.analyticsServiceOtherResult())
    }

}