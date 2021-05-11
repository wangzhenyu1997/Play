package com.wang.play.ui.fragment.main.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wang.play.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var firstViewModel: FirstViewModel
    private lateinit var binding: FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        firstViewModel =
            ViewModelProvider(this).get(FirstViewModel::class.java)
        binding =
            FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




    }


}