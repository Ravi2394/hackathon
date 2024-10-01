package com.example.firstcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcompose.model.AddEmployeRequest
import com.example.firstcompose.model.AddEmployeeResponse

import com.example.firstcompose.repository.AddEmployessRespository

import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.HandleApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEmployeeViewModel @Inject constructor(private val addEmployessRespository: AddEmployessRespository, var handleApiResponse: HandleApiResponse) : ViewModel() {

    val addEmployeeState: MutableStateFlow<ResponseData<AddEmployeeResponse>> = MutableStateFlow(
        ResponseData.Empty())
    fun employeeList(
       emp_name: String, email: String, mobile: String,vehicle: String, vehicle_type: String
    ) {
        viewModelScope.launch {
            try {
                val addEmployeRequest = AddEmployeRequest(
                    emp_name = emp_name,email = email,mobile = mobile,vehicle = vehicle,vehicle_type = vehicle_type
                )
                addEmployessRespository.getData(addEmployeRequest).onStart {
                    addEmployeeState.value = ResponseData.Loading()
                }.catch {
                    //  loginState.value = ResponseData.Error(null, context.failMsg(error = it.toString()))
                }.collect {
                    addEmployeeState.value = handleApiResponse.handleApiResponse(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace();
                Log.e("exc",""+ex)
            }
        }
    }
}