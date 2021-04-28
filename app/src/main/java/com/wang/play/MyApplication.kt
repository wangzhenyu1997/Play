package com.wang.play

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.tencent.mmkv.MMKV
import com.wang.mylibrary.util.LogUtil
import com.wang.play.data.login.User


class MyApplication : Application() {

    companion object {
        lateinit var context: Context


    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ProcessLifecycleOwner.get().lifecycle.addObserver(MyApplicationObserver())


        //初始化MMKV
        val rootDir = MMKV.initialize(this)


    }


    //观察应用的生命周期
    class MyApplicationObserver() : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun applicationPause() {
            //Toast.makeText(MyApplication.context, "您的应用已退出，请小心", Toast.LENGTH_SHORT).show()
        }

    }
}