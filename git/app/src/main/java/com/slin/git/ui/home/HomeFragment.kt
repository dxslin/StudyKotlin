package com.slin.git.ui.home

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.slin.core.ui.CoreFragment
import com.slin.git.R
import com.slin.git.ui.login.LoginActivity
import org.kodein.di.instance
import retrofit2.Retrofit

class HomeFragment : CoreFragment() {

    private val retrofit: Retrofit by instance()

    private lateinit var homeViewModel: HomeViewModel

    override val layoutResId: Int = R.layout.fragment_home

    override fun initView(view: View) {
        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory(retrofit)).get(HomeViewModel::class.java)

        val textView: TextView = view.findViewById(R.id.text_home)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        textView.setOnClickListener {
//            homeViewModel.auth()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

        }

    }

}
