package com.slin.git.ui.home.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.slin.core.net.Results
import com.slin.git.ui.home.data.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val TAG: String? = HomeViewModel::class.simpleName


    private val _homeViewState = MutableLiveData<HomeViewState>(HomeViewState.initial())
    val homeViewState: MutableLiveData<HomeViewState> = _homeViewState

//    val pageListLiveData:LiveData<List<ReceivedEvent>>

    fun queryEvents(index: Int) {
        viewModelScope.launch {
            val result = homeRepository.queryReceivedEvents(index)
            when (result) {
                is Results.Success -> {

                }
                is Results.Failure -> {

                }
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }

}
