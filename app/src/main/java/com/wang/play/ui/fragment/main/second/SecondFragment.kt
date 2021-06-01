package com.wang.play.ui.fragment.main.second

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.tencent.mmkv.MMKV
import com.wang.play.MyApplication
import com.wang.play.UtilString
import com.wang.play.adapter.FooterAdapter
import com.wang.play.adapter.test.TestAdapter
import com.wang.play.databinding.FragmentSecondBinding
import com.wang.play.datasource.service.test.CreateTestService
import com.wang.play.repository.test.TestRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class SecondFragment : Fragment() {


    private val secondViewModel: SecondViewModel by viewModels {
        SecondViewModel
            .SecondViewModelFactory(TestRepository(CreateTestService.testCreate()))
    }

    private var _binding: FragmentSecondBinding? = null
    private val binding
        get() = _binding!!

    //初始化所需fragmentSecondRecyclerView的Adapter
    private val testAdapter = TestAdapter()

    private val kv = MMKV.defaultMMKV()

    //这是一个用来发送网络请求的launch协程
    private var searchJob: Job? = null
    private val scope = MainScope()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentSecondBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()

        //encode 编码
        kv?.encode(UtilString.SecondFragmentSearch, binding.fragmentSecondEditText.text.toString())

        scope.cancel()
        _binding = null


    }

    //初始化界面
    private fun initView() {

        //给RecyclerView每个item之间设置节点
        val decoration =
            DividerItemDecoration(MyApplication.context, DividerItemDecoration.VERTICAL)
        binding.fragmentSecondRecyclerView.addItemDecoration(decoration)

        //我们常见的底部load more就是添加页脚，但是这里的Header不是我们项目中在列表最顶部添加一个item的意思，而是和load more类似的概念。
        //也就是说如果我们添加了一个页头，那么只有在PagingSource中返回LoadResult.Page的时候prevKey不为null才会显示出来，
        //所以如果我们从第一页开始加载是看不到这个Header的，如果我们一开始加载的页数是第5页，那么我们在往上滑动的时候，才能看到我们的Header

        binding.fragmentSecondRecyclerView.adapter = testAdapter.withLoadStateHeaderAndFooter(
            header = FooterAdapter { testAdapter.retry() },
            footer = FooterAdapter { testAdapter.retry() }
        )

        testAdapter.addLoadStateListener {

            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.fragmentSecondRecyclerView.visibility = View.VISIBLE
                    binding.fragmentSecondError.visibility = View.INVISIBLE
                    binding.fragmentSecondLoading.visibility = View.INVISIBLE
                }
                is LoadState.Loading -> {
                    binding.fragmentSecondRecyclerView.visibility = View.INVISIBLE
                    binding.fragmentSecondError.visibility = View.INVISIBLE
                    binding.fragmentSecondLoading.visibility = View.VISIBLE
                    binding.fragmentSecondLoading.playAnimation()
                }
                is LoadState.Error -> {
                    binding.fragmentSecondRecyclerView.visibility = View.INVISIBLE
                    binding.fragmentSecondError.visibility = View.VISIBLE
                    binding.fragmentSecondLoading.visibility = View.INVISIBLE
                    binding.fragmentSecondError.playAnimation()
                }


            }
        }


        val temp: String = kv?.decodeString(UtilString.SecondFragmentSearch, "cat") ?: "cat"
        binding.fragmentSecondEditText.setText(temp)

        search(temp)
        initSearch()

    }


    //请求数据并加载到adapter上
    private fun search(query: String) {

        //避免重复请求
        searchJob?.cancel()
        searchJob = scope.launch(Dispatchers.IO) {
            secondViewModel.searchHit(query).collect {
                testAdapter.submitData(it)
            }
        }
    }

    private fun initSearch() {

        //在我们编辑完之后点击软键盘上的各种键才会触发
        //imeOptions=”actionUnspecified” –> EditorInfo.IME_ACTION_UNSPECIFIED
        //imeOptions=”actionNone” –> EditorInfo.IME_ACTION_NONE
        //imeOptions=”actionGo” –> EditorInfo.IME_ACTION_GO
        //imeOptions=”actionSearch” –> EditorInfo.IME_ACTION_SEARCH
        //imeOptions=”actionSend” –> EditorInfo.IME_ACTION_SEND
        //imeOptions=”actionNext” –> EditorInfo.IME_ACTION_NEXT
        //imeOptions=”actionDone” –> EditorInfo.IME_ACTION_DONE
        binding.fragmentSecondEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //收起软键盘
                val inputMethodManager = view?.context?.getSystemService(
                    INPUT_METHOD_SERVICE
                ) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                binding.fragmentSecondRecyclerView.scrollToPosition(0)

                updateHitListFromInput()
                true
            } else {
                false
            }
        }


    }

    private fun updateHitListFromInput() {
        binding.fragmentSecondEditText.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

}


// findNavController().popBackStack()
//sharedElementEnterTransition = TransitionInflater.from(requireContext())
//            .inflateTransition(R.transition.shared_image)

