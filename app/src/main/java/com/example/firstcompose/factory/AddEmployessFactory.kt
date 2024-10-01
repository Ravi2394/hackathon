package com.example.firstcompose.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firstcompose.repository.AddEmployessRespository
import com.example.firstcompose.utils.HandleApiResponse
import com.example.firstcompose.viewmodel.AddEmployeeViewModel


class AddEmployessFactory (private val addEmployessRespository: AddEmployessRespository, private var handleApiResponse: HandleApiResponse) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEmployeeViewModel::class.java)) {
            return AddEmployeeViewModel(addEmployessRespository,handleApiResponse) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}