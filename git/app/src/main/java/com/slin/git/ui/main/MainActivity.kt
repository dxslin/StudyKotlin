package com.slin.git.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.slin.git.R
import com.slin.git.base.BaseActivity
import com.slin.git.databinding.ActivityMainBinding
import com.slin.git.ext.hide
import com.slin.git.ext.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        var INSTANCE: MainActivity? = null

        fun showLogin() {
            INSTANCE?.apply {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_home_to_login)
            }
        }

    }

//    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        INSTANCE = this
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.apply {
//            val navView: BottomNavigationView = findViewById(R.id.nav_view)

            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//                setOf(
//                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//                )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)


            navView.setupWithNavController(navController)
            lifecycleScope.launchWhenResumed {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.navigation_home,
                        R.id.navigation_dashboard,
                        R.id.navigation_notifications -> navView.show()
                        else -> navView.hide()
                    }
                }
            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        INSTANCE = null
    }

}
