package com.wang.play.ui.fragment.login.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wang.play.MyApplication
import com.wang.play.databinding.FragmentRegisterBinding
import com.wang.play.ui.activity.login.LoginViewModel
import com.wang.play.ui.activity.main.MainActivity


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        registerCheck()


    }

    //注册检查
    private fun registerCheck() {


        //登录成功后跳转界面
        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            if (it.result) {
                startActivity(Intent(MyApplication.context, MainActivity::class.java))
            } else {
                binding.fragmentRegisterProgressBar.visibility = View.INVISIBLE
                binding.fragmentRegisterRegister.isEnabled = true
            }
        })

        //检查用户名和密码是否符合要求
        viewModel.loginFrom.observe(viewLifecycleOwner, Observer {
            if (!it.isUserNameValid) {
                Toast.makeText(MyApplication.context, "登录名错误", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            if (!it.isPasswordValid) {
                Toast.makeText(MyApplication.context, "密码错误", Toast.LENGTH_SHORT).show()
                return@Observer
            }
        })

        binding.fragmentRegisterRegister.setOnClickListener {

            val username = binding.fragmentRegisterUsername.text.toString().replace(" ", "")
            val password = binding.fragmentRegisterPassword.text.toString().replace(" ", "")

            viewModel.loginDataChanged(username, password)

            if (viewModel.loginFrom.value?.isDataValid == true) {
                binding.fragmentRegisterRegister.isEnabled = false
                binding.fragmentRegisterProgressBar.visibility = View.VISIBLE
                viewModel.register(username, password)
            }

        }
    }

}