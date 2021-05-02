package com.wang.play.ui.activity.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.leancloud.AVUser
import com.wang.play.MyApplication
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class LoginViewModel : ViewModel() {


    private val _loginFrom = MutableLiveData<LoginFromState>().also {
        it.value = LoginFromState()
    }
    val loginFrom: LiveData<LoginFromState> = _loginFrom


    private val _loginResult = MutableLiveData<LoginResult>().also {
        it.value = LoginResult()
    }
    val loginResult: LiveData<LoginResult> = _loginResult


    //注册
    fun register(registerUsername: String, registerPassword: String) {

        AVUser().apply {
            username = registerUsername
            password = registerPassword
            signUpInBackground().subscribe(object : Observer<AVUser> {

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: AVUser) {
                    login(registerUsername, registerPassword)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        MyApplication.context,
                        "${e.message ?: "没有错误信息"}",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginResult.postValue(LoginResult(false))
                }

                override fun onComplete() {}
            })
        }


    }


    //登录
    fun login(loginUsername: String, loginPassword: String) {

        AVUser.logIn(loginUsername, loginPassword)
            .subscribe(object : Observer<AVUser?> {

                override fun onError(throwable: Throwable) {
                    Toast.makeText(
                        MyApplication.context,
                        "${throwable.message ?: "没有错误信息"}",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginResult.postValue(LoginResult(false))
                }

                override fun onComplete() {}

                override fun onNext(t: AVUser) {

                    _loginResult.postValue(LoginResult(true))
                }

                override fun onSubscribe(d: Disposable) {}
            })
    }

    //检查用户名和密码的有效性
    fun loginDataChanged(username: String, password: String) {

        val usernameValid = isUserNameValid(username)
        val passwordValid = isPasswordValid(password)

        _loginFrom.value =
            LoginFromState(usernameValid, passwordValid, usernameValid && passwordValid)

    }


    private fun isUserNameValid(username: String) =
        username.contains("@")

    private fun isPasswordValid(password: String) =
        password.length > 5

}