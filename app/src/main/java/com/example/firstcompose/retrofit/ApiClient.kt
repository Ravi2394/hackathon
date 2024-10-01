package com.example.firstcompose.retrofit
import android.app.Application
import android.content.Context
import com.example.firstcompose.utils.HandleApiResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    @Provides
    @Singleton
    fun providerRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://b851-14-195-110-27.ngrok-free.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
    @Provides
    @Singleton
    fun providesResponseCodeCheck(): HandleApiResponse {
        return HandleApiResponse()
    }
    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val requestWithHeaders: Request = originalRequest.newBuilder()
                .addHeader("device-type", "1")
                .addHeader("version-code", "4.2")
                .addHeader("user-login", "1")
                .build()
            chain.proceed(requestWithHeaders)
        }
    }
}