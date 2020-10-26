package com.slin.git.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slin.git.api.entity.SearchHistory
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _historyData: MutableLiveData<MutableList<SearchHistory>> = MutableLiveData()
    val historyData: LiveData<MutableList<SearchHistory>> = _historyData


    fun search(keywords: String) {
        //go to search result activity
        viewModelScope.launch {
            addSearchHistory(keywords)
        }
    }


    fun query() {
        viewModelScope.launch {
            //查询历史
            val data = repository.querySearchHistory().toMutableList()
            //列表倒叙
            data.reverse()
            _historyData.postValue(data)
        }
    }

    private suspend fun addSearchHistory(value: String) {
        repository.insertSearchHistory(SearchHistory.SearchType.REPOS, value)
        query()
    }

}