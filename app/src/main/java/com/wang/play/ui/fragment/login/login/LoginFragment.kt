package com.wang.play.ui.fragment.login.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tencent.mmkv.MMKV
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.UtilString
import com.wang.play.databinding.FragmentLoginBinding
import com.wang.play.ui.activity.login.LoginViewModel
import com.wang.play.ui.activity.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    //MMKV
    private val kv = MMKV.defaultMMKV()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewInit()

        loginCheck()


    }


    //初始化界面
    private fun viewInit() {
        //判断是否有记住的用户名
        if (kv?.decodeBool(UtilString.ActivityLoginRememberUsername, false) == true) {
            binding.fragmentLoginRememberUsername.isChecked = true
            binding.fragmentLoginUsername.setText(
                kv?.decodeString(
                    UtilString.ActivityLoginUsername,
                    ""
                ) ?: ""
            )
        }
    }


    //登录检查
    private fun loginCheck() {

        //登录成功后跳转界面
        viewModel.loginResult.observe(viewLifecycleOwner, {
            if (it.result) {
                startActivity(Intent(MyApplication.context, MainActivity::class.java))
            } else {
                binding.fragmentLoginProgressBar.visibility = View.INVISIBLE
                binding.fragmentLoginLogin.isEnabled = true
            }
        })

        //检查登录名和密码
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

        binding.fragmentLoginLogin.setOnClickListener {

            val username = binding.fragmentLoginUsername.text.toString().replace(" ", "")
            val password = binding.fragmentLoginPassword.text.toString().replace(" ", "")

            viewModel.loginDataChanged(username, password)

            if (viewModel.loginFrom.value?.isDataValid == true) {
                binding.fragmentLoginLogin.isEnabled = false
                binding.fragmentLoginProgressBar.visibility = View.VISIBLE
                loginRemember()
                viewModel.login(username, password)
            }
        }
    }

    //设置记住密码，自动登录
    private fun loginRemember() {

        if (binding.fragmentLoginRememberUsername.isChecked) {
            kv?.encode(UtilString.ActivityLoginRememberUsername, true)
            kv?.encode(
                UtilString.ActivityLoginUsername,
                binding.fragmentLoginUsername.text.toString()
            )
        } else {
            kv?.encode(UtilString.ActivityLoginRememberUsername, false)
        }
    }


}

