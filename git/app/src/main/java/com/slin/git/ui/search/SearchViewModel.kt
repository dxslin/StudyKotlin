package com.slin.git.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slin.git.api.entity.SearchHistory
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _historyData: MutableLiveData<List<SearchHistory>> = MutableLiveData()
    val historyData: LiveData<List<SearchHistory>> = _historyData


    fun search(keywords: String) {
        //go to search result activity
        viewModelScope.launch {
            addSearchHistory(keywords)
        }
    }


    fun query() {
        viewModelScope.launch {
            _historyData.postValue(
                repository.querySearchHistory().toList()
            )
        }
    }

    private suspend fun addSearchHistory(value: String) {
        repository.insertSearchHistory(SearchHistory.SearchType.REPOS, value)
    }

}