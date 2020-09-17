package com.slin.git.ui.home.view

import androidx.lifecycle.*
import com.slin.core.net.Results
import com.slin.git.entity.ReceivedEvent
import com.slin.git.ui.home.data.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val TAG: String? = HomeViewModel::class.simpleName


    private val _homeViewState = MutableLiveData(HomeViewState.initial())
    val homeViewState: LiveData<HomeViewState> = _homeViewState

    private val _pageListLiveData: MutableLiveData<List<ReceivedEvent>> = MutableLiveData()
    val pageListLiveData: LiveData<List<ReceivedEvent>> = _pageListLiveData

    fun queryEvents(index: Int) {
        viewModelScope.launch {
            val result = homeRepository.queryReceivedEvents(index)
            when (result) {
                is Results.Success -> {
                    _pageListLiveData.postValue(result.data)
                    _homeViewState.postValue(HomeViewState(isLoading = false))
                }
                is Results.Failure -> {
                    _homeViewState.postValue(HomeViewState(isLoading = false))
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
