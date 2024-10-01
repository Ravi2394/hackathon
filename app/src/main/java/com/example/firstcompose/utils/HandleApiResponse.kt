package com.example.firstcompose.utils
import android.util.Log
import com.example.firstcompose.retrofit.ResponseData
import retrofit2.Response
import javax.inject.Inject

class HandleApiResponse @Inject constructor(){
    fun <T> handleApiResponse(response: Response<T>): ResponseData<T> {
        Log.e("==response==",""+response.code())
        return when (response.code()) {
            200 -> {
                if (response.isSuccessful) {
                    Log.e("==response==",""+response.body().toString())
                    ResponseData.Success(response.body())
                } else {
                    ResponseData.Error(response.body(), "")
                }
            }
            400 -> ResponseData.Error(response.body(),"Bad Request")
            401 -> ResponseData.Error(response.body(),"Unauthorized: Please login again")
            500 -> ResponseData.Error(response.body(),"Server Error: Please try again later")
            else -> ResponseData.Error(response.body(),"Unknown Error: ${response.code()}")
        }
    }
}