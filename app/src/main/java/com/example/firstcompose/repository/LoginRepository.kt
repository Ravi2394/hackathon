package com.example.firstcompose.repository
import com.example.firstcompose.model.LoginRequest
import com.example.firstcompose.model.LoginResponse
import com.example.firstcompose.retrofit.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getData(hashMap: LoginRequest): Flow<Response<LoginResponse>> {
        return flow {
            val login = apiInterface.login(hashMap)
            emit(login)
        }.flowOn(Dispatchers.IO)
    }

}