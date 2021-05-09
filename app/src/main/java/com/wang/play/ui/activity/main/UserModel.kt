package com.wang.play.ui.activity.main

import kotlinx.coroutines.delay
import kotlin.random.Random

class UserModel {

    suspend fun doLogin(callback: OnDoLoginStateChange) {

        callback.onLoading()

        val num: Int = Random.nextInt(2)

        delay(1000)

        if (num == 0) {
            callback.onLoginSuccess()
        } else {
            callback.onLoginFailed()
        }

    }

    suspend fun  checkUserState(username: String, block: suspend (Int) -> Unit) {
        //0 不可以
        //1 可以
        block.invoke(Random.nextInt(2))
    }


    interface OnDoLoginStateChange {

        suspend fun onLoading()

        suspend fun onLoginSuccess()

        suspend fun onLoginFailed()

    }

}