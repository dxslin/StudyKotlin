package com.slin.git.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slin.core.net.status.SvsState
import com.slin.git.databinding.ItemHeaderLoadStateBinding
import com.slin.sate_view_switcher.StateViewSwitcher


/**
 * author: slin
 * date: 2020/10/21
 * description:
 *
 */

fun PagingDataAdapter<out Any, out RecyclerView.ViewHolder>.withLoadStateRefresh(refresh: LoadStateAdapter<*>): ConcatAdapter {
    addLoadStateListener { loadStates ->
        refresh.loadState = loadStates.refresh
    }
    return ConcatAdapter(refresh, this)
}

fun PagingDataAdapter<out Any, out RecyclerView.ViewHolder>.withLoadStateRefreshAndFooter(
    refresh: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        refresh.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }
    return ConcatAdapter(refresh, this, footer)
}

class RefreshLoadStateAdapter(private val adapter: PagingDataAdapter<out Any, out RecyclerView.ViewHolder>) :
    LoadStateAdapter<HeaderLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: HeaderLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HeaderLoadStateViewHolder {
        return HeaderLoadStateViewHolder(
            ItemHeaderLoadStateBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        ) {
            adapter.retry()
        }
    }


}


class HeaderLoadStateViewHolder(val binding: ItemHeaderLoadStateBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    private val stateViewSwitcher by lazy {
        StateViewSwitcher(binding.vPlaceholder) {
            retry()
        }
    }

    fun bind(state: LoadState) {
        when (state) {
            is LoadState.NotLoading -> {
                stateViewSwitcher.stateChange(SvsState.LoadSuccess(getAdapterItemCount() > 0))
            }
            is LoadState.Loading -> {
                stateViewSwitcher.stateChange(SvsState.Loading(getAdapterItemCount() > 0))
            }
            is LoadState.Error -> {
                stateViewSwitcher.stateChange(
                    SvsState.LoadFail(state.error)
                )
            }
        }
    }

    private fun getAdapterItemCount(): Int {
        return bindingAdapter?.itemCount ?: 0
    }

}
