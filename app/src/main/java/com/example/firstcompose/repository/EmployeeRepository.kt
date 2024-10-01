package com.example.firstcompose.repository

import com.example.firstcompose.model.EmployeeRequest
import com.example.firstcompose.model.EmployeeResponse
import com.example.firstcompose.retrofit.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class EmployeeRepository  @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getData(hashMap: EmployeeRequest): Flow<Response<EmployeeResponse>> {
        return flow {
            val employee = apiInterface.employee(hashMap)
            emit(employee)
        }.flowOn(Dispatchers.IO)
    }

}