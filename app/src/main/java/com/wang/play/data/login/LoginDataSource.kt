package com.wang.play.data.login

import com.tencent.mmkv.MMKV
import com.wang.play.UtilString

object LoginDataSource {


    //从远程获得数据
    private fun getUser(): User {

        val kv = MMKV.defaultMMKV()
        return User(
            kv?.decodeString(UtilString.StorageUserName, "") ?: "",
            kv?.decodeString(UtilString.StoragePassword, "") ?: ""
        )

    }

    //修改远端数据
    fun setData(username: String, password: String) {
        val kv = MMKV.defaultMMKV()
        kv?.encode(UtilString.StorageUserName, username)
        kv?.encode(UtilString.StoragePassword, password)
    }


    fun login(username: String, password: String): Boolean {

        val user: User = getUser()
        return user.username == username && user.password == password
    }


}