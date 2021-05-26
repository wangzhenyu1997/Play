package com.wang.play.ui.fragment.main.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.wang.play.MyApplication
import com.wang.play.adapter.FooterAdapter
import com.wang.play.adapter.NewsAdapter
import com.wang.play.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private val secondViewModel: SecondViewModel by viewModels()
    private var _binding: FragmentSecondBinding? = null
    private val binding
        get() = _binding!!


    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentSecondBinding.inflate(inflater, container, false)

        binding.fragmentSecondRecyclerView.adapter =
            newsAdapter.withLoadStateFooter(FooterAdapter { newsAdapter.retry() })

        return binding.root


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        initListener()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //初始化页面
    private fun init() {
        lifecycleScope.launch(Dispatchers.IO)
        {
            secondViewModel.getNews().collect {
                newsAdapter.submitData(it)
            }
        }
    }

    //注册监听器
    private fun initListener() {
        newsAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.fragmentSecondRecyclerView.visibility = View.VISIBLE
                    binding.fragmentSecondLoading.visibility = View.INVISIBLE
                    binding.fragmentSecondError.visibility = View.INVISIBLE
                    binding.fragmentSecondError.cancelAnimation()
                    binding.fragmentSecondLoading.cancelAnimation()
                }
                is LoadState.Loading -> {
                    binding.fragmentSecondRecyclerView.visibility = View.INVISIBLE
                    binding.fragmentSecondLoading.visibility = View.VISIBLE
                    binding.fragmentSecondError.visibility = View.INVISIBLE
                    binding.fragmentSecondError.cancelAnimation()
                    binding.fragmentSecondLoading.playAnimation()
                }
                is LoadState.Error -> {
                    binding.fragmentSecondRecyclerView.visibility = View.INVISIBLE
                    binding.fragmentSecondLoading.visibility = View.INVISIBLE
                    binding.fragmentSecondError.visibility = View.VISIBLE
                    binding.fragmentSecondLoading.cancelAnimation()
                    binding.fragmentSecondError.playAnimation()

                    Toast.makeText(MyApplication.context, "请检查网络", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        
    }

    // findNavController().popBackStack()
    //sharedElementEnterTransition = TransitionInflater.from(requireContext())
    //            .inflateTransition(R.transition.shared_image)


}

