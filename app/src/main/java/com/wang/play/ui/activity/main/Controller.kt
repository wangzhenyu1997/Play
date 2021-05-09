package com.wang.play.ui.activity.main

class Controller {

    private val userModel by lazy {
        UserModel()
    }


    fun checkUsernameState(username: String, callback: OnCheckUsernameStateResultCallback) {


    }

    interface OnCheckUsernameStateResultCallback {
        //不存在
        fun onNotExist()

        fun onExist()
    }

}