package com.wang.play.data.login

object LoginRepository {


    fun login(username: String, password: String): Boolean {
        return LoginDataSource.login(username, password)
    }


}