package com.slin.study.kotlin.ui.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */
class VMLiveDataViewModel : ViewModel() {


    val count: MutableStateFlow<Int> = MutableStateFlow(0)

    val name: MutableLiveData<String> = MutableLiveData("slin")


}