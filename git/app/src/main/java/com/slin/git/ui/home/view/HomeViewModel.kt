package com.slin.git.ui.home.view

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slin.git.api.entity.ReceivedEvent
import com.slin.git.ui.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author: slin
 * date: 2020/9/16
 * description: 首页ViewModel
 *
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    private val TAG: String? = HomeViewModel::class.simpleName


    private val _homeViewState = MutableLiveData(HomeViewState.initial())
    val homeViewState: LiveData<HomeViewState> = _homeViewState

    private val _pageListLiveData: MutableLiveData<PagingData<ReceivedEvent>> = MutableLiveData()
    val pageListLiveData: LiveData<PagingData<ReceivedEvent>> = _pageListLiveData

    val receiveEventFlow = homeRepository.queryReceivedEvents()
        .cachedIn(viewModelScope)

    fun queryEvents() {

//        viewModelScope.launch {
//            val result = homeRepository.queryReceivedEvents(index)
//            when (result) {
//                is Results.Success -> {
//                    _pageListLiveData.postValue(result.data)
//                    _homeViewState.postValue(HomeViewState(isLoading = false))
//                }
//                is Results.Failure -> {
//                    _homeViewState.postValue(HomeViewState(isLoading = false))
//                }
//            }
//        }
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }

}
