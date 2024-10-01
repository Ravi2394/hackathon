package com.example.firstcompose.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firstcompose.repository.EmployeeRepository

import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.viewmodel.EmployeeListViewModel


class EmployeeViewModelFactory(private val employeeRepository: EmployeeRepository, private var handleApiResponse: HandleApiResponse) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeListViewModel::class.java)) {
            return EmployeeListViewModel(employeeRepository,handleApiResponse) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}