package com.example.mvvmsampleapp.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModelProvider

interface AuthListener {




    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)


}