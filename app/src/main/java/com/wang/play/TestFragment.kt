package com.wang.play

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.databinding.FragmentTestBinding

class TestFragment : BaseFragment<FragmentTestBinding>() {

    private val temp: Int by lazy {
        requireArguments().getInt("test")
    }

    
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTestBinding.inflate(inflater, container, false)















    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplicationLogUtil.d("TodayTest", "onAttach   $temp")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplicationLogUtil.d("TodayTest", "onCreate   $temp")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MyApplicationLogUtil.d("TodayTest", "onCreateView   $temp")

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplicationLogUtil.d("TodayTest", "onActivityCreated   $temp")
    }

    override fun onStart() {
        super.onStart()
        MyApplicationLogUtil.d("TodayTest", "onStart   $temp")
    }

    override fun onResume() {
        super.onResume()
        MyApplicationLogUtil.d("TodayTest", "onResume   $temp")
    }

    override fun onPause() {
        super.onPause()
        MyApplicationLogUtil.d("TodayTest", "onPause   $temp")
    }

    override fun onStop() {
        super.onStop()
        MyApplicationLogUtil.d("TodayTest", "onStop   $temp")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TodayTest", "onDestroy   $temp")
    }

    override fun onDetach() {
        super.onDetach()
        MyApplicationLogUtil.d("TodayTest", "onDetach   $temp")
    }
}

