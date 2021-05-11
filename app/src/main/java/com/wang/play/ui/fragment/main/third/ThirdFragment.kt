package com.wang.play.ui.fragment.main.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wang.play.R
import com.wang.play.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var thirdViewModel: ThirdViewModel
    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thirdViewModel =
            ViewModelProvider(this).get(ThirdViewModel::class.java)
        binding =
            FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root
    }




}