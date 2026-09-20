package com.example.dukaansetu.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var authToken: String? = null

    fun setAuthToken(token: String?) {
        authToken = token
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor { authToken })
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val apiService: DukaanSetuApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DukaanSetuApiService::class.java)
    }
}
