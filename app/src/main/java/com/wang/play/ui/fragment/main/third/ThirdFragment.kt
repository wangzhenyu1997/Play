package com.wang.play.ui.fragment.main.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.adapter.TestAdapter
import com.wang.play.databinding.FragmentThirdBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


//https://gank.io/api/v2/data/category/<category>/type/<type>/page/<page>/count/<count>
//请求方式: GET
//注:
//
//category 可接受参数 All(所有分类) | Article | GanHuo | Girl
//type 可接受参数 All(全部类型) | Android | iOS | Flutter | Girl ...，即分类API返回的类型数据
//count: [10, 50]
//page: >=1
//示例:
//
//获取妹子列表
//https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
//获取Android干货列表
//https://gank.io/api/v2/data/category/GanHuo/type/Android/page/1/count/10


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