package com.wang.play

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wang.mylibrary.util.MyApplicationLogUtil

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //使状态栏字体为深色
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        //test 1
        MyApplicationLogUtil.d("AAAA", "AAAAAAAAAA")


        //test 2
        MyApplicationLogUtil.d("BBBB", "BBBBBBBB")

        //test 3
        MyApplicationLogUtil.d("CCCC", "CCCCCCCC")

    }
}