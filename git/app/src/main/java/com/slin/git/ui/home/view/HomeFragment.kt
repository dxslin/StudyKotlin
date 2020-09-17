package com.slin.git.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slin.git.base.BaseFragment
import com.slin.git.databinding.FragmentHomeBinding
import com.slin.git.entity.UserInfo
import com.slin.git.manager.UserManager
import org.kodein.di.DI
import org.kodein.di.instance

/**
 * 首页，received_events
 */
class HomeFragment : BaseFragment() {

    override val di: DI by DI.lazy() {
        extend(super.di)
        import(homeModule)
    }

    private val viewModel: HomeViewModel by instance()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!UserManager.isLoggedIn) {
            UserManager.INSTANCE = UserInfo.dxslin
            UserManager.isLoggedIn = true

//            startActivity(Intent(context, LoginActivity::class.java))
//            return
        }
        binding.apply {
            adapter = HomeAdapter()
            rvEventsList.adapter = adapter

            viewModel.pageListLiveData.observe(this@HomeFragment.viewLifecycleOwner, {
                adapter.submitList(it)
            })
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.queryEvents(0)
    }

}
