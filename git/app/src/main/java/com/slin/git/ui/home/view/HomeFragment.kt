package com.slin.git.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.slin.core.logger.logd
import com.slin.core.net.status.SvsState
import com.slin.git.R
import com.slin.git.base.BaseFragment
import com.slin.git.databinding.FragmentHomeBinding
import com.slin.git.entity.UserInfo
import com.slin.git.manager.UserManager
import com.slin.git.ui.common.FooterLoadStateAdapter
import com.slin.sate_view_switcher.StateViewSwitcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    private lateinit var stateViewSwitcher: StateViewSwitcher

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
            stateViewSwitcher = StateViewSwitcher(rvEventsList) {
                adapter.retry()
            }

            adapter = ReceivedEventAdapter()
            val loadStateAdapter = FooterLoadStateAdapter(adapter)
            rvEventsList.adapter = adapter.withLoadStateFooter(loadStateAdapter)

            adapter.addLoadStateListener { loadState ->
                logd { "onViewCreated: ${loadState}" }
                when (loadState.source.refresh) {
                    is LoadState.NotLoading ->
                        stateViewSwitcher.stateChange(SvsState.LoadSuccess(adapter.itemCount > 0))
                    is LoadState.Loading ->
                        stateViewSwitcher.stateChange(SvsState.Loading(adapter.itemCount > 0))
                    is LoadState.Error -> stateViewSwitcher.stateChange(
                        SvsState.LoadFail((loadState.source.refresh as LoadState.Error).error)
                    )
                }
            }
            lifecycleScope.launch {
                viewModel.receiveEventFlow.collectLatest {
                    adapter.submitData(it)
                }

            }
        }

    }

    fun startSearchFragment(view: View){
        findNavController().navigate(R.id.action_home_to_search)
    }

}
