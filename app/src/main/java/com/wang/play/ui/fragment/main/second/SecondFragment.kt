package com.wang.play.ui.fragment.main.second

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var secondViewModel: SecondViewModel
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
        MyApplicationLogUtil.d("TestNowAAASecond","onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TestNowAAASecond","onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        MyApplicationLogUtil.d("TestNowAAASecond","onDetach")
    }


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


   // findNavController().popBackStack()






    //sharedElementEnterTransition = TransitionInflater.from(requireContext())
    //            .inflateTransition(R.transition.shared_image)


}