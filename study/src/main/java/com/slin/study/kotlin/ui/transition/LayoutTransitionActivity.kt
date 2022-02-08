package com.slin.study.kotlin.ui.transition

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityLayoutTransitionBinding
import kotlin.random.Random

class LayoutTransitionActivity : BaseActivity() {

    var transitionEnable = true

    private lateinit var binding: ActivityLayoutTransitionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutTransitionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Layout Transition"
        setShowBackButton(true)

        val list = MutableList(20) {
            "item $it"
        }

        val adapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_layout_transition_text, list) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.apply {
                    setText(R.id.text, item)
                    getView<View>(R.id.itemRemove).setOnClickListener {
                        if (transitionEnable) {
                            TransitionManager.beginDelayedTransition(recyclerView)
                        }
                        list.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                }
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
//        recyclerView.addItemDecoration(GridDividerItemDivider(10, 10, Color.TRANSPARENT))


        binding.btnAddItem.setOnClickListener {
            if (transitionEnable) {
                TransitionManager.beginDelayedTransition(binding.recyclerView)
            }
            val index = Random.nextInt(0, list.size)
            list.add(index, "Add $index")
            adapter.notifyItemInserted(index)
        }

    }


}