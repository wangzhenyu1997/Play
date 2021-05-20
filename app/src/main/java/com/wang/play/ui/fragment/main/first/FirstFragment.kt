package com.wang.play.ui.fragment.main.first

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.R
import com.wang.play.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private val firstViewModel: FirstViewModel by viewModels()
    private lateinit var binding: FragmentFirstBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplicationLogUtil.d("TestNowAAAFirst", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplicationLogUtil.d("TestNowAAAFirst", "onCreate")
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_first_to_testFragment)
        }

        MyApplicationLogUtil.d("TestNowAAAFirst", "onActivityCreated")

    }

    override fun onStart() {
        super.onStart()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onStart")
    }

    override fun onResume() {
        super.onResume()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onResume")
    }

    override fun onPause() {
        super.onPause()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onPause")
    }

    override fun onStop() {
        super.onStop()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        MyApplicationLogUtil.d("TestNowAAAFirst", "onDetach")
    }


    // binding.button.setOnClickListener {
    //
    //            val bundle = Bundle()
    //            bundle.putString("testData", "923340")
    //            findNavController().navigate(
    //                R.id.action_navigation_first_to_navigation_activity_loginActivity,
    //                bundle,
    //                null,
    //                null
    //            )
    //        }
    //
    //        binding.button2.setOnClickListener {
    //
    //
    //            val extras: FragmentNavigator.Extras =
    //                FragmentNavigatorExtras(binding.imageView to "testTransition")
    //
    //            findNavController().navigate(
    //                R.id.action_navigation_first_to_navigation_second,
    //                null,
    //                null,
    //                extras
    //            )
    //        }

}