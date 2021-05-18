package com.wang.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.databinding.FragmentTestBinding

class TestFragment : Fragment() {


    private lateinit var binding: FragmentTestBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentTestBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        MyApplicationLogUtil.d("TestNowAAATest","onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TestNowAAATest","onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        MyApplicationLogUtil.d("TestNowAAATest","onDetach")
    }



}