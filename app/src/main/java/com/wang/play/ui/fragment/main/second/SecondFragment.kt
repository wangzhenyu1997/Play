package com.wang.play.ui.fragment.main.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wang.play.R
import com.wang.play.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var secondViewModel: SecondViewModel
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        secondViewModel =
            ViewModelProvider(this).get(SecondViewModel::class.java)
        binding =
            FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root


    }


}