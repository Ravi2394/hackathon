package com.example.firstcompose.retrofit
import com.example.firstcompose.model.AddEmployeRequest
import com.example.firstcompose.model.AddEmployeeResponse
import com.example.firstcompose.model.EmployeeRequest
import com.example.firstcompose.model.EmployeeResponse
import com.example.firstcompose.model.LoginRequest
import com.example.firstcompose.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("Employee/employeeLogin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("Employee/employeeList")
    suspend fun employee(@Body employeeRequest: EmployeeRequest): Response<EmployeeResponse>

    @Headers("Content-Type: application/json")
    @POST("Employee/registerEmployee")
    suspend fun addEmployee(@Body addEmployeRequest: AddEmployeRequest): Response<AddEmployeeResponse>


//    companion object {
//        fun getApi(): ApiInterface {
//            return ApiClient.providerRetrofit().create(ApiInterface::class.java)
//        }
//    }



}