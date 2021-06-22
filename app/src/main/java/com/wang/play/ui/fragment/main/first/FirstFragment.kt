package com.wang.play.ui.fragment.main.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.wang.play.BaseFragment
import com.wang.play.R
import com.wang.play.TestFragment
import com.wang.play.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment<FragmentFirstBinding>() {

    //private val firstViewModel: FirstViewModel by viewModels()

    private var temp = 1

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFirstBinding.inflate(inflater, container, false)




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val bundle = bundleOf("test" to temp)
        childFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_first_container, TestFragment::class.java, bundle)
        }

//        binding.button.setOnClickListener {
//            temp++
//            val tempBundle = bundleOf("test" to temp)
//            childFragmentManager.commit {
//                setReorderingAllowed(true)
//                add(R.id.fragment_first_container, TestFragment::class.java, tempBundle)
//            }
//        }


    }


}