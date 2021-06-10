package com.wang.play.ui.fragment.main.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wang.play.databinding.FragmentFourthBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class FourthFragment : Fragment() {


    private var _binding: FragmentFourthBinding? = null
    private val binding
        get() = _binding!!


//    private val fourthViewModel: FourthViewModel by viewModels()


    //自定义的协程作用域，在视图被摧毁后取消
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentFourthBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)






    }


    override fun onDestroyView() {
        super.onDestroyView()


        //取消协程作用域
        scope.cancel()
    }


}