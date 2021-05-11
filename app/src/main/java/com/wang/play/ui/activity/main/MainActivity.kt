package com.wang.play.ui.activity.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.wang.play.BaseActivity
import com.wang.play.R
import com.wang.play.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

    }


    //初始化导航
    private fun initNavigation() {

        //设置标题栏
        setSupportActionBar(binding.activityMainToolBar)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_first,
                R.id.navigation_second,
                R.id.navigation_third
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.activityMainNavView.setupWithNavController(navController)
    }

}