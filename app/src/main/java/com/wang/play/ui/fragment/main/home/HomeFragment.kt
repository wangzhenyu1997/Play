package com.wang.play.ui.fragment.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wang.play.MyApplication
import com.wang.play.databinding.FragmentHomeBinding
import com.wang.play.ui.activity.main.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), UserModel.OnDoLoginStateChange {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private val userModel by lazy {
        UserModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding =
            FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()


    }


    private fun initListener() {

        binding.button.setOnClickListener {
            toLogin()

        }
    }


    private fun toLogin() {
        //登录前检查

        lifecycleScope.launch(Dispatchers.IO) {

            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            userModel.checkUserState(username) {
                when (it) {
                    0 -> {
                        Toast.makeText(MyApplication.context, "用户名已经存在", Toast.LENGTH_SHORT)
                            .show()
                    }
                    1 -> {
                        userModel.doLogin(this)
                        withContext(Dispatchers.Main) { binding.button.isEnabled = false }
                    }
                }
            }


        }
    }

    override suspend fun onLoading() {
        withContext(Dispatchers.Main)
        {
            binding.textView.text = "正在加载中"
        }
    }



    
    override suspend fun onLoginSuccess() {
        withContext(Dispatchers.Main)
        {
            binding.textView.text = "加载成功"
        }
    }

    override suspend fun onLoginFailed() {
        withContext(Dispatchers.Main)
        {
            binding.textView.text = "加载失败"
        }
    }

}