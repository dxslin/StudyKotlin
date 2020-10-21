package com.slin.git.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slin.git.databinding.ItemFooterLoadStateBinding


/**
 * author: slin
 * date: 2020/9/18
 * description:
 *
 */
class FooterLoadStateAdapter(private val adapter: PagingDataAdapter<out Any, out RecyclerView.ViewHolder>) :
    LoadStateAdapter<FooterLoadStateAdapter.FooterLoadViewHolder>() {

    override fun onBindViewHolder(holder: FooterLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterLoadViewHolder {
        return FooterLoadViewHolder(
            ItemFooterLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) {
            adapter.retry()
        }
    }


    class FooterLoadViewHolder(
        private val binding: ItemFooterLoadStateBinding,
        private val retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                this.loadState = loadState
                tvLoadText.setOnClickListener {
                    retry()
                }
                executePendingBindings()
            }
        }
    }
}

