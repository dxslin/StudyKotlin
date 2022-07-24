package com.slin.study.kotlin.ui.transition.fragment

import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.longan.statusBarColor
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.FragmentTransitionDetailBinding
import com.slin.study.kotlin.ui.transition.TransitionData
import com.slin.study.kotlin.ui.transition.TransitionDetailActivity

class TransitionDetailFragment : BaseFragment(R.layout.fragment_transition_detail) {

    private lateinit var binding: FragmentTransitionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also { view ->
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransitionDetailBinding.bind(view)

        // 沉浸式
        requireActivity().statusBarColor = Color.TRANSPARENT
        // Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val transitionData: TransitionData? =
            arguments?.getParcelable(TransitionDetailActivity.INTENT_TRANSITION_DATA)
        transitionData?.let {
            binding.toolbar.title = transitionData.title
            binding.ivPageIcon.setImageResource(transitionData.imgRes)
            binding.tvPageSubName.text = transitionData.abstract
            binding.tvPageDetail.text = transitionData.detail

            val pos =
                requireArguments().getInt(TransitionDetailActivity.INTENT_TRANSITION_INDEX)
            binding.toolbar.transitionName = getString(R.string.key_transition_title) + pos
            binding.ivPageIcon.transitionName =
                getString(R.string.key_transition_image_photo) + pos
            binding.tvPageSubName.transitionName =
                getString(R.string.key_transition_abstract) + pos
            binding.tvPageDetail.transitionName =
                getString(R.string.key_transition_detail) + pos
        }
//            sharedElementEnterTransition =
//                TransitionInflater.from(requireContext())
//                    .inflateTransition(android.R.transition.move)
        // 共享元素的动画
        val changeBounds = ChangeBounds()
        changeBounds.duration = 800
        sharedElementEnterTransition = changeBounds
        sharedElementReturnTransition = changeBounds
    }

    override fun onDestroyView() {
        view?.fitsSystemWindows = false
        super.onDestroyView()
    }

    companion object {
        fun newInstance(index: Int, transitionData: TransitionData): TransitionDetailFragment {
            return TransitionDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(TransitionDetailActivity.INTENT_TRANSITION_INDEX, index)
                    putParcelable(TransitionDetailActivity.INTENT_TRANSITION_DATA, transitionData)
                }
            }
        }
    }
}
