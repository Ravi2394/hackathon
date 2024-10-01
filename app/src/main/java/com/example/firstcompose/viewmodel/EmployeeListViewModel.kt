package com.example.firstcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcompose.model.EmployeeRequest
import com.example.firstcompose.model.EmployeeResponse
import com.example.firstcompose.repository.EmployeeRepository
import com.example.firstcompose.retrofit.ResponseData
import com.example.firstcompose.utils.HandleApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(private val employeeRepository: EmployeeRepository, var handleApiResponse: HandleApiResponse) : ViewModel() {

    val employeeState: MutableStateFlow<ResponseData<EmployeeResponse>> = MutableStateFlow(ResponseData.Empty())
    fun employeeList(
        id: String
    ) {
        viewModelScope.launch {
            try {
                val employeeRequest = EmployeeRequest(
                    emp_Id = id,
                )
                employeeRepository.getData(employeeRequest).onStart {
                    employeeState.value = ResponseData.Loading()
                }.catch {
                    //  loginState.value = ResponseData.Error(null, context.failMsg(error = it.toString()))
                }.collect {
                    employeeState.value = handleApiResponse.handleApiResponse(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace();
                Log.e("exc",""+ex)
            }
        }
    }
}