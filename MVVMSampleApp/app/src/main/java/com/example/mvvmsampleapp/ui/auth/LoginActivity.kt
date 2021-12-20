package com.example.mvvmsampleapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.utils.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    lateinit var viewModel: AuthViewModel

//    val factory = SampleViewModelFactory("sample")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingLayout: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
//        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        bindingLayout.viewmodel = viewModel

        viewModel.authListener = this

    }

    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(message: String) {
        toast(message)
    }


}