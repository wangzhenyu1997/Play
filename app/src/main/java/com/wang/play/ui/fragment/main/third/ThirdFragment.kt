package com.wang.play.ui.fragment.main.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.adapter.TestAdapter
import com.wang.play.databinding.FragmentThirdBinding
import com.wang.play.datasource.room.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ThirdFragment : Fragment() {

    private val thirdViewModel: ThirdViewModel by viewModels()
    private lateinit var binding: FragmentThirdBinding

    //TestAdapter
    private val testAdapter = TestAdapter()

    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.fragmentThirdRecyclerView.adapter = testAdapter


        //初始化Observe
        initObserve()

        initListener()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        MyApplicationLogUtil.d("TestNowAAAThird", "onDestroyView")
        scope.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplicationLogUtil.d("TestNowAAAThird", "onDestroy")
    }


    override fun onDetach() {
        super.onDetach()
        MyApplicationLogUtil.d("TestNowAAAThird", "onDetach")
    }


    private fun initObserve() {
//        useDatabase?.getAllUsers()?.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
    }


    private fun initListener() {


        binding.fragmentThirdButton1.setOnClickListener {
            scope.launch(Dispatchers.IO) {
                thirdViewModel.loadBeautiful(testAdapter, "1", "20")
            }

        }


//        binding.fragmentThirdButton1.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO)
//            {
//
//            }
//        }
    }

}