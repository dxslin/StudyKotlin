package com.slin.git.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.slin.core.logger.logd
import com.slin.git.R
import com.slin.git.api.entity.UserInfo
import com.slin.git.base.BaseFragment
import com.slin.git.databinding.FragmentHomeBinding
import com.slin.git.manager.UserManager
import com.slin.git.ui.common.ContentLoadStateAdapter
import com.slin.git.ui.common.FooterLoadStateAdapter
import com.slin.git.ui.common.withLoadStateRefreshAndFooter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.kodein.di.DI
import org.kodein.di.instance

/**
 * 首页，received_events
 */
class HomeFragment : BaseFragment() {

    override val di: DI by DI.lazy {
        extend(super.di)
        import(homeModule)
    }

    private val viewModel: HomeViewModel by instance()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: ReceivedEventAdapter

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
            UserManager.userInfo.value = UserInfo.dxslin
//            findNavController().navigate(R.id.action_home_to_login)
//            return
        }
        binding.apply {
            adapter = ReceivedEventAdapter()

            rvEventsList.adapter = adapter
                .withLoadStateRefreshAndFooter(
                    content = ContentLoadStateAdapter(adapter),
                    footer = FooterLoadStateAdapter(adapter)
                )
//            rvEventsList.itemAnimator = SpringAddItemAnimator()

            ivSearch.setOnClickListener { startSearchFragment() }
            srlRefresh.setOnRefreshListener {
                adapter.refresh()
            }

            lifecycleScope.launchWhenCreated {
                adapter.loadStateFlow.collectLatest { loadState ->
                    logd { "onViewCreated: $loadState" }
                    srlRefresh.isRefreshing = loadState.refresh is LoadState.Loading
                }
            }
            lifecycleScope.launchWhenCreated {
                adapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.Loading }
                    .collect { rvEventsList.scrollToPosition(0) }
            }
            lifecycleScope.launchWhenCreated {
                viewModel.receiveEventFlow.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

    }


    private fun startSearchFragment() {
        val extras = FragmentNavigatorExtras(
            Pair(binding.ivSearch, ViewCompat.getTransitionName(binding.ivSearch) ?: "")
        )
        findNavController().navigate(R.id.action_home_to_search, null, null, extras)
    }

}
