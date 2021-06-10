package com.wang.play.ui.activity.main

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.wang.play.BaseActivity
import com.wang.play.R
import com.wang.play.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    //绑定视图
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //控制页面的退出
    private var isExit: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        //初始化Navigation
        initNavigation()

    }


    override fun onSupportNavigateUp(): Boolean {
        // return super.onSupportNavigateUp()原函数
        val controller = binding.activityMainNavHostFragment.findNavController()
        return controller.navigateUp()
    }

    //定义双击退出
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return if (isExit) {
                super.onKeyDown(keyCode, event)
            } else {
                Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show()
                isExit = true
                lifecycleScope.launch(Dispatchers.Main)
                {
                    delay(2000)
                    isExit = false
                }
                true
            }

        }

        return super.onKeyDown(keyCode, event)
    }



    //初始化导航
    private fun initNavigation() {

        //设置标题栏
        setSupportActionBar(binding.activityMainToolBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment)
                    as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_first,
                R.id.navigation_second,
                R.id.navigation_third,
                R.id.navigation_fourth
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.activityMainNavView.setupWithNavController(navController)
    }


}







