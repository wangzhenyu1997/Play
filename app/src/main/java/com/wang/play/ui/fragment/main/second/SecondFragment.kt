package com.wang.play.ui.fragment.main.second

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private val secondViewModel: SecondViewModel by viewModels()
    private lateinit var binding: FragmentSecondBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MyApplicationLogUtil.d("TestNowAAASecond", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TestNowAAASecond", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        MyApplicationLogUtil.d("TestNowAAASecond", "onDetach")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.fragmentSecondImageView.load("https://ae01.alicdn.com/kf/Ua867fc827ce646efa6718e273d543a783.jpg")
    }

    // findNavController().popBackStack()


    //sharedElementEnterTransition = TransitionInflater.from(requireContext())
    //            .inflateTransition(R.transition.shared_image)


}