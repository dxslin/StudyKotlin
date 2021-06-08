package com.slin.study.kotlin.ui.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */
class VMLiveDataViewModel : ViewModel() {


    val count: MutableLiveData<Int> = MutableLiveData(0)

    val name: MutableLiveData<String> = MutableLiveData("slin")


}