package com.slin.git.ui.home.view

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
            adapter,
            ItemFooterLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    class FooterLoadViewHolder(
        private val adapter: PagingDataAdapter<out Any, out RecyclerView.ViewHolder>,
        private val binding: ItemFooterLoadStateBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                this.loadState = loadState
                tvLoadText.setOnClickListener {
                    adapter.retry()
                }
            }
        }
    }
}

