package com.wang.play.ui.activity.welcome

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mmkv.MMKV
import com.wang.mylibrary.util.LogUtil
import com.wang.play.UtilString
import com.wang.play.data.login.LoginRepository
import com.wang.play.data.login.User
import com.wang.play.databinding.ActivityWelcomeBinding
import com.wang.play.ui.activity.login.LoginActivity
import com.wang.play.ui.activity.main.MainActivity


class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeBinding: ActivityWelcomeBinding
    //MMKV
    private val kv = MMKV.defaultMMKV()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(welcomeBinding.root)

        lottieInit()

    }

    //判断是否需要登录
    private fun loginOrNot() {

        if (kv?.decodeBool(UtilString.ActivityLoginAutoLogin, false) == true) {
            val user: User = kv?.decodeParcelable(
                UtilString.ActivityLoginUser,
                User::class.java,
                User("noUserName", "noPassword")
            ) ?: User("noUserName", "noPassword")

            if (LoginRepository.login(user.username, user.password)) {
                //成功登录
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return
            }
        }

        //默认登录方式
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

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


}