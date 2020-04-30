package com.slin.study.kotlin.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.slin.study.kotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))

        viewBindingTest()
        return binding.root
    }


    private fun viewBindingTest() {
        homeViewModel.text.observe(this, Observer {
            binding.tvHome.text = it
        })
        binding.tvTextBind.apply {
            text = "View bind test"
            setOnClickListener { _ ->
                text = "bind view clicked"
            }
        }

        binding.llHomeContent.tvHomeContent.apply {
            text = "1111"
            setTextColor(Color.WHITE)
        }
        binding.llHomeContent.root.setBackgroundColor(Color.GRAY)

    }

}