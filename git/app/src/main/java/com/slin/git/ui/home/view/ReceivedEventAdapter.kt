package com.slin.git.ui.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slin.git.databinding.ItemHomeEventsBinding
import com.slin.git.entity.ReceivedEvent
import com.slin.git.entity.receivedEventsDiff


/**
 * author: slin
 * date: 2020/9/17
 * description:
 *
 */
class ReceivedEventAdapter :
    PagingDataAdapter<ReceivedEvent, ReceiveEventViewHolder>(receivedEventsDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveEventViewHolder {
        return ReceiveEventViewHolder(
            ItemHomeEventsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReceiveEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}


class ReceiveEventViewHolder(private val binding: ItemHomeEventsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(receivedEvent: ReceivedEvent?) {
        binding.apply {
            event = receivedEvent
            executePendingBindings()
        }
    }

}
