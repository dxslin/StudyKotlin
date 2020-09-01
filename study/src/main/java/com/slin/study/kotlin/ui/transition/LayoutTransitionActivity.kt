package com.slin.study.kotlin.ui.transition

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_layout_transition.*
import kotlin.random.Random

class LayoutTransitionActivity : BaseActivity() {

    var transitionEnable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_transition)

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
//        recyclerView.addItemDecoration(GridDividerItemDivider(10, 10, Color.TRANSPARENT))


        btn_addItem.setOnClickListener {
            if (transitionEnable) {
                TransitionManager.beginDelayedTransition(recyclerView)
            }
            val index = Random.nextInt(0, list.size)
            list.add(index, "Add $index")
            adapter.notifyItemInserted(index)
        }

    }


}