package com.wang.play.ui.fragment.login.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tencent.mmkv.MMKV
import com.wang.play.MyApplication
import com.wang.play.UtilString
import com.wang.play.data.login.User
import com.wang.play.databinding.FragmentLoginBinding
import com.wang.play.ui.activity.login.LoginViewModel
import com.wang.play.ui.activity.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

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
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewInit()

        loginCheck()


    }


    //登录检查
    private fun loginCheck() {

        viewModel.welcomeResult.observe(viewLifecycleOwner, Observer {
            if (it.result) {
                startActivity(Intent(MyApplication.context, MainActivity::class.java))
            }
        })

        viewModel.welcomeFrom.observe(viewLifecycleOwner, Observer {
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

            loginRemember()

            viewModel.login(
                binding.fragmentLoginUsername.text.toString(),
                binding.fragmentLoginPassword.text.toString()
            )
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
        if (binding.fragmentLoginAutoLogin.isChecked) {
            kv?.encode(UtilString.ActivityLoginAutoLogin, true)
            kv?.encode(
                UtilString.ActivityLoginUser, User(
                    binding.fragmentLoginUsername.text.toString(),
                    binding.fragmentLoginPassword.text.toString()
                )
            )
        } else {
            kv?.encode(UtilString.ActivityLoginAutoLogin, false)
        }

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
        if (kv?.decodeBool(UtilString.ActivityLoginAutoLogin, false) == true) {
            binding.fragmentLoginAutoLogin.isChecked = true
        }

    }
}

