package com.slin.git

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.slin.git.base.BaseActivity
import com.slin.git.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        }


    }

}
