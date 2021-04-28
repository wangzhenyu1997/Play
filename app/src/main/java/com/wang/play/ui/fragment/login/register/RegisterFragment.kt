package com.wang.play.ui.fragment.login.register

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wang.play.MyApplication
import com.wang.play.databinding.FragmentRegisterBinding
import com.wang.play.ui.activity.login.LoginViewModel
import com.wang.play.ui.activity.main.MainActivity


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: LoginViewModel


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
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        registerCheck()

    }

    //注册检查
    private fun registerCheck() {

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

        binding.fragmentRegisterRegister.setOnClickListener {

            viewModel.register(
                binding.fragmentRegisterUsername.text.toString(),
                binding.fragmentRegisterPassword.text.toString()
            )
        }
    }

}