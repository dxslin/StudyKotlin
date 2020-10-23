package com.slin.git.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slin.git.api.entity.SearchHistory
import com.slin.git.api.entity.SearchHistoryDiff
import com.slin.git.databinding.ItemSearchHistoryBinding


/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */
class SearchHistoryAdapter :
    PagingDataAdapter<SearchHistory, SearchHistoryAdapter.ViewHolder>(SearchHistoryDiff) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ViewHolder(val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchHistory: SearchHistory?) {
            searchHistory?.let {
                binding.tvKeywords.text = searchHistory.keyword
            }
        }
    }
}
