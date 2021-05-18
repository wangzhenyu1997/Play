package com.wang.play.ui.fragment.main.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.adapter.FlowerAdapter
import com.wang.play.data.main.testResponse
import com.wang.play.databinding.FragmentFourthBinding
import com.wang.play.repository.CreateService
import com.wang.play.datasource.room.database.AppDatabase
import com.wang.play.datasource.room.entity.User
import com.wang.play.datasource.service.testAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FourthFragment : Fragment() {


    private lateinit var binding: FragmentFourthBinding
    private val fourthViewModel: FourthViewModel by viewModels()

    private val flowerAdapter = FlowerAdapter()


    //操作数据库
    private val useDatabase = AppDatabase.getInstance(MyApplication.context)?.userDao()

    //自定义的协程作用域，在视图被摧毁后取消
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      
        binding =
            FragmentFourthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.fragmentFourthRecyclerView.adapter = flowerAdapter

fourthViewModel.run {  }


        scope.launch(Dispatchers.Main)
        {

            try {
                //获取数据
                val response: testResponse = CreateService.create<testAPI>().testLoad()
                MyApplicationLogUtil.d("FourthFragmentLog", response.toString())


                val list: MutableList<User> = mutableListOf()
                for (it in response.data) {
                    list.add(User("Giao", it.name))
                }
                flowerAdapter.submitList(list)
            } catch (e: Exception) {
                Toast.makeText(MyApplication.context, "请检查网络连接", Toast.LENGTH_SHORT).show()
            }

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()


        //取消协程作用域
        scope.cancel()
    }


//      对数据库的操作
//    private fun initRoom() {
//        val flowerAdapter = FlowerAdapter()
//        binding.fragmentFourthRecyclerView.adapter = flowerAdapter
//
//        val userList = mutableListOf<User>()
//
//        for (it in 0..3) {
//            userList.add(User(firstName = "Wang", lastName = "Flower${it}"))
//        }
//
//
//        useDatabase?.getAllUsers()
//            ?.observe(viewLifecycleOwner) {
//                flowerAdapter.submitList(it)
//            }
//
//        //插入
//        binding.button.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val a = useDatabase?.insertUser(*userList.toTypedArray())
//                MyApplicationLogUtil.d("FourthFragmentLog", Arrays.toString(a))
//            }
//
//        }
//
//        //更新
//        binding.button2.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val user1 = User("邵", "雪聪")
//                user1.uid = 1
//                val user2 = User("谢", "天依")
//                user2.uid = 2
//                val user3 = User("邵", "雪聪")
//                user3.uid = 3
//                val user4 = User("谢", "天依")
//                user4.uid = 4
//                val a = useDatabase?.updateUser(user1, user2, user3, user4)
//                MyApplicationLogUtil.d("FourthFragmentLog", a.toString())
//            }
//
//        }
//
//
//        //删除
//        binding.button3.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO)
//            {
//                val user1 = User("邵", "雪聪")
//                user1.uid = 1
//                val user2 = User("谢", "天依")
//                user2.uid = 2
//                val user3 = User("邵", "雪聪")
//                user3.uid = 3
//                val user4 = User("谢", "天依")
//                user4.uid = 4
//                val a = useDatabase?.deleteUser(user1, user2, user3, user4)
//                MyApplicationLogUtil.d("FourthFragmentLog", a.toString())
//
//            }
//        }
//
//
//        //删除全部
//        binding.button4.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val a = useDatabase?.deleteAllUser()
//                MyApplicationLogUtil.d("FourthFragmentLog", a.toString())
//
//            }
//        }
//    }
}