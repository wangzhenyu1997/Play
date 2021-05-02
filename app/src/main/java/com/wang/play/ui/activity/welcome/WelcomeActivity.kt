package com.wang.play.ui.activity.welcome

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.leancloud.AVUser
import com.wang.play.databinding.ActivityWelcomeBinding
import com.wang.play.ui.activity.login.LoginActivity
import com.wang.play.ui.activity.main.MainActivity


class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(welcomeBinding.root)


        lottieInit()

    }

    //Lottie初始化，结束后判断是否需要登录
    private fun lottieInit() {

        val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1000)
        animator.addUpdateListener {
            welcomeBinding.activityWelcomeLottie.progress = it.animatedValue as Float
        }
        animator.start()

        welcomeBinding.activityWelcomeLottie.addAnimatorUpdateListener {
            if (it.animatedFraction == 1f) {
                animator.cancel()
                loginOrNot()
            }
        }
    }

    //判断是否需要登录
    private fun loginOrNot() {

        //获取本地保存的 session token
        val currentUser = AVUser.getCurrentUser()
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }


}