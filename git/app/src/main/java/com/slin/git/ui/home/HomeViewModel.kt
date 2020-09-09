package com.slin.git.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.slin.core.logger.log
import com.slin.git.stroage.bean.LoginRequestModel
import com.slin.git.stroage.remote.LoginService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import timber.log.Timber

class HomeViewModel(private val retrofit: Retrofit) : ViewModel() {

    private val TAG: String? = HomeViewModel::class.simpleName


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: MutableLiveData<String> = _text


    fun auth() {
        viewModelScope.launch {
            try {
                log { "authorizations start" }
                Timber.d("timber authorizations start")
                val message = retrofit.create(LoginService::class.java)
                        .authorizations(LoginRequestModel.generate())
                        .message()
                text.postValue(message)
                log { "authorizations: ${message.toString()}" }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val retrofit: Retrofit) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(retrofit) as T
    }

}
