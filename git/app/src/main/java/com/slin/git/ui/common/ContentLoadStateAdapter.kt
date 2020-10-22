package com.slin.git.ui.common

import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slin.core.net.status.SvsState
import com.slin.sate_view_switcher.StateViewSwitcher


/**
 * author: slin
 * date: 2020/10/21
 * description: 使用[ConcatAdapter]结合[StateViewSwitcher]管理加载状态
 *
 *  使用方式：
 *  ```
 *  val newAdapter = adapter.withLoadStateRefresh(ContentLoadStateAdapter(adapter))
 *  ```
 *
 */

fun PagingDataAdapter<out Any, out RecyclerView.ViewHolder>.withLoadStateRefresh(content: ContentLoadStateAdapter): ConcatAdapter {
    addLoadStateListener { loadStates ->
        content.loadState = loadStates.source.refresh
    }
    return ConcatAdapter(content, this)
}

fun PagingDataAdapter<out Any, out RecyclerView.ViewHolder>.withLoadStateRefreshAndFooter(
    content: ContentLoadStateAdapter,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        content.loadState = loadStates.source.refresh
        footer.loadState = loadStates.append
    }
    return ConcatAdapter(content, this, footer)
}

class ContentLoadStateAdapter(private val adapter: PagingDataAdapter<out Any, out RecyclerView.ViewHolder>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var stateViewSwitcher: StateViewSwitcher? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        stateViewSwitcher = StateViewSwitcher(recyclerView) {
            adapter.retry()
        }
    }

    var loadState: LoadState = LoadState.NotLoading(false)
        set(state) {
            field = state
            stateViewSwitcher?.change(loadState, adapter.itemCount > 0)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val placeholder = View(parent.context)
        return object : RecyclerView.ViewHolder(placeholder) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //ignore
    }

    override fun getItemCount(): Int {
        return 0
    }


}

fun StateViewSwitcher.change(state: LoadState, haveData: Boolean) {
    when (state) {
        is LoadState.NotLoading -> {
            stateChange(SvsState.LoadSuccess(haveData))
        }
        is LoadState.Loading -> {
            stateChange(SvsState.Loading(haveData))
        }
        is LoadState.Error -> {
            stateChange(
                SvsState.LoadFail(state.error)
            )
        }
    }
}
