package com.habitbread.main.api

import com.habitbread.main.base.BaseApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerImpl {

    private val interceptor: AccessTokenInterceptor = AccessTokenInterceptor()
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private const val BASE_URL = "http://dev.habitbread.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val APIService : HabitBreadAPI = retrofit.create(HabitBreadAPI::class.java)
}

class AccessTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BaseApplication.preferences.googleIdToken
        val builder = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(builder)
    }
}

