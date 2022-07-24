package com.slin.study.kotlin.ui.transition.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.SlideInRightAnimation
import com.dylanc.longan.statusBarColor
import com.google.android.material.color.MaterialColors
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.constant.ConstantResource
import com.slin.study.kotlin.databinding.FragmentTransitionListBinding
import com.slin.study.kotlin.ui.transition.TransitionData
import com.slin.study.kotlin.ui.transition.ViewHolder
import kotlin.random.Random

class TransitionListFragment : BaseFragment(R.layout.fragment_transition_list) {

    private lateinit var binding: FragmentTransitionListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarColor()
        binding = FragmentTransitionListBinding.bind(view)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val data = mutableListOf<TransitionData>()
        for (i in 1..20) {
            data.add(
                TransitionData(
                    ConstantResource.IMAGE_RESOURCE[
                            Random.nextInt(
                                0,
                                ConstantResource.IMAGE_RESOURCE.size
                            )
                    ],
                    "Title_$i",
                    "this is abstract_$i",
                    getString(R.string.transition_page_detail)
                )
            )
        }

        val adapter = TransitionAdapter { viewHolder, transitionData ->
            startDetailFragment(viewHolder, transitionData)
        }
        adapter.adapterAnimation = SlideInRightAnimation()
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )

        adapter.setList(data)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "Transition Fragment"
    }

    private fun startDetailFragment(holder: ViewHolder, item: TransitionData) {
        parentFragmentManager.beginTransaction()
            .addSharedElement(holder.ivPhoto, ViewCompat.getTransitionName(holder.ivPhoto)!!)
            .addSharedElement(holder.tvTitle, ViewCompat.getTransitionName(holder.tvTitle)!!)
            .addSharedElement(holder.tvAbstract, ViewCompat.getTransitionName(holder.tvAbstract)!!)
            .addSharedElement(holder.tvDetail, ViewCompat.getTransitionName(holder.tvDetail)!!)
            .addToBackStack(null)
            .hide(this)
            .add(
                R.id.fc_container,
                TransitionDetailFragment.newInstance(holder.layoutPosition, item)
            )
            .commit()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            updateStatusBarColor()
        }
    }

    private fun updateStatusBarColor() {
        val activity = (requireActivity() as AppCompatActivity)
        activity.statusBarColor =
            MaterialColors.getColor(requireContext(), R.attr.colorPrimaryDark, Color.WHITE)
    }

    class TransitionAdapter(private val onItemClick: (ViewHolder, TransitionData) -> Unit) :
        BaseQuickAdapter<TransitionData, ViewHolder>(R.layout.item_horizontal_transition) {
        override fun convert(holder: ViewHolder, item: TransitionData) {
            holder.ivPhoto.setImageResource(item.imgRes)
            holder.tvTitle.text = item.title
            holder.tvAbstract.text = item.abstract
            holder.tvDetail.text = item.detail

            val pos = holder.layoutPosition
            holder.ivPhoto.transitionName =
                context.getString(R.string.key_transition_image_photo) + pos
            holder.tvTitle.transitionName = context.getString(R.string.key_transition_title) + pos
            holder.tvAbstract.transitionName =
                context.getString(R.string.key_transition_abstract) + pos
            holder.tvDetail.transitionName = context.getString(R.string.key_transition_detail) + pos

            holder.itemView.setOnClickListener {
                onItemClick(holder, item)
            }
        }
    }
}
