package com.slin.git.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.slin.core.net.Errors
import com.slin.core.net.status.State
import com.slin.core.net.status.StateViewSwitcher
import com.slin.git.base.BaseFragment
import com.slin.git.databinding.FragmentNotificationsBinding
import java.io.IOException

class NotificationsFragment : BaseFragment() {

    private val notificationsViewModel: NotificationsViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    private lateinit var binding: FragmentNotificationsBinding

    private lateinit var stateViewSwitcher: StateViewSwitcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            notificationsViewModel.text.observe(viewLifecycleOwner, { textNotifications.text = it })

            stateViewSwitcher = StateViewSwitcher(recyclerView)

            btnInitial.setOnClickListener { stateViewSwitcher.stateChange(State.Initial) }
            btnLoading.setOnClickListener { stateViewSwitcher.stateChange(State.Loading) }
            btnLoadSuccess.setOnClickListener { stateViewSwitcher.stateChange(State.LoadSuccess) }
            btnLoadFailNoData.setOnClickListener { stateViewSwitcher.stateChange(State.NoData) }
            btnLoadFailNoNetwork.setOnClickListener {
                stateViewSwitcher.stateChange(
                    State.LoadFail(
                        Errors.NoNetworkError
                    )
                )
            }
            btnLoadFailError.setOnClickListener {
                stateViewSwitcher.stateChange(
                    State.LoadFail(
                        Errors.IOError(IOException("IOError"))
                    )
                )
            }


        }
    }

}
