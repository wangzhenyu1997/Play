package com.wang.play.ui.activity.login

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.BaseActivity
import com.wang.play.R
import com.wang.play.databinding.ActivityLoginBinding
import com.wang.play.ui.fragment.login.login.LoginFragment
import com.wang.play.ui.fragment.login.register.RegisterFragment

class LoginActivity : BaseActivity() {

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }

    private lateinit var loginBinding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplicationLogUtil.d("LoginActivityLog", "onCreate")

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        viewPager2Init()

        viewListener()
    }

    override fun onStart() {
        super.onStart()
        MyApplicationLogUtil.d("TestFragmentLoginActivityLog", "onStart")
    }

    override fun onResume() {
        super.onResume()
        MyApplicationLogUtil.d("TestFragmentLoginActivityLog", "onResume")
    }

    override fun onPause() {
        super.onPause()
        MyApplicationLogUtil.d("TestFragmentLoginActivityLog", "onPause")
    }

    override fun onStop() {
        super.onStop()
        MyApplicationLogUtil.d("TestFragmentLoginActivityLog", "onStop")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TestFragmentLoginActivityLog", "onDestroy")
    }

    //配置ViewPager2
    private fun viewPager2Init() {
        //设置各个Fragment()
        loginBinding.activityLoginViewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int) = when (position) {
                0 -> LoginFragment()
                else -> RegisterFragment()
            }
        }
        //设置转换动画,来自Android文档：https://developer.android.google.cn/training/animation/screen-slide-2
        //object : ViewPager2.PageTransformer
        //override fun transformPage(page: View, position: Float)
        loginBinding.activityLoginViewPager2.setPageTransformer { page, position ->
            page.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> {
                        alpha = 0f
                    }
                    position <= 1 -> {
                        //修改默认的滑动过渡来收缩页面
                        val scaleFactor = MIN_SCALE.coerceAtLeast(1 - kotlin.math.abs(position))
                        val verMargin = pageHeight * (1 - scaleFactor) / 2
                        val horMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horMargin - verMargin / 2
                        } else {
                            horMargin + verMargin / 2
                        }
                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                        // Fade the page relative to its size.
                        alpha =
                            (MIN_ALPHA + (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> {
                        alpha = 0f
                    }
                }
            }
        }

        TabLayoutMediator(
            loginBinding.activityLoginTabLayout,
            loginBinding.activityLoginViewPager2
        ) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.activity_login_login)
                else -> tab.text = resources.getString(R.string.activity_login_register)
            }
        }.attach()
    }


    //监听键盘的弹出与收回
    private fun viewListener() {

        loginBinding.activityLogin.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener { //获取当前可见显示框的rectangle
            val rect = Rect()
            loginBinding.activityLogin.getWindowVisibleDisplayFrame(rect)
            if (loginBinding.activityLogin.rootView.height - rect.bottom > 250) {
                loginBinding.activityLoginLottie.visibility = View.INVISIBLE
            } else {
                loginBinding.activityLoginLottie.visibility = View.VISIBLE
            }
        })
    }

}