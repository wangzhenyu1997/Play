package com.wang.play.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wang.play.data.login.LoginDataSource
import com.wang.play.data.login.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {


    private val _loginFrom = MutableLiveData<LoginFromState>().also {
        it.value = LoginFromState()
    }
    val welcomeFrom: LiveData<LoginFromState> = _loginFrom

    private val _loginResult = MutableLiveData<LoginResult>().also {
        it.value = LoginResult()
    }
    val welcomeResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String) {

        loginDataChanged(username, password)

        viewModelScope.launch {
            if (_loginFrom.value?.isDataValid == true) {
                _loginResult.postValue(LoginResult(LoginRepository.login(username, password)))
            }
        }

    }

    fun register(username: String, password: String) {

        loginDataChanged(username, password)

        viewModelScope.launch {
            if (_loginFrom.value?.isDataValid == true) {

                LoginDataSource.setData(username, password)
                _loginResult.postValue(LoginResult(LoginRepository.login(username, password)))
            }
        }

    }

    private fun loginDataChanged(username: String, password: String) {

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