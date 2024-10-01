package com.example.firstcompose.repository

import com.example.firstcompose.model.AddEmployeRequest
import com.example.firstcompose.model.AddEmployeeResponse
import com.example.firstcompose.retrofit.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class AddEmployessRespository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getData(hashMap: AddEmployeRequest): Flow<Response<AddEmployeeResponse>> {
        return flow {
            val addEmployee = apiInterface.addEmployee(hashMap)
            emit(addEmployee)
        }.flowOn(Dispatchers.IO)
    }
}
