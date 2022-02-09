package com.slin.study.kotlin.ui.jetpack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.slin.study.kotlin.ui.jetpack.hlit.HiltInjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * author: slin
 * date: 2020/11/20
 * description:
 *
 *
 */
@HiltViewModel
class HiltInjectViewModel @Inject constructor(
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

    fun test(observer: Observer<Unit>) {
        observer.onChanged(Unit)
        val field = observer.javaClass.getDeclaredField("this$0")
        field.get(observer)
        Log.d("Test", "$observer this$0: ${field.get(observer)}")
    }

}