package com.example.firstcompose.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firstcompose.repository.LoginRepository
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.viewmodel.LoginViewModel

class LoginViewModelFactory(private val loginRepository: LoginRepository,private var handleApiResponse: HandleApiResponse) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository,handleApiResponse) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}