package com.slin.study.kotlin.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slin.study.kotlin.util.AssetsUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val copyState = AssetsUtils.copyAssetsToDownload("patches").flowOn(Dispatchers.IO)
}
