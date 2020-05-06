package com.slin.study.kotlin.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.slin.study.kotlin.databinding.ActivityViewBindBinding

class ViewBindActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBindBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewBindBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        viewBindingTest()
    }

    private fun viewBindingTest() {
        binding.tvHome.text = " View Bind"
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
