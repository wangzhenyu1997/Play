package com.wang.play.ui.fragment.main.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private val firstViewModel: FirstViewModel by viewModels()
    private lateinit var binding: FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MyApplicationLogUtil.d("TestNowAAAFirst", "onCreateView")

        binding =
            FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

}