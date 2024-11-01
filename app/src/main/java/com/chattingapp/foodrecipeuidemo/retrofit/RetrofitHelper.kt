package com.chattingapp.foodrecipeuidemo.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "http://192.168.1.158:8765/"

    // Create an OkHttpClient without an Interceptor
    private val httpClient = OkHttpClient.Builder()
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)  // Attach OkHttpClient to Retrofit
        .addConverterFactory(ScalarsConverterFactory.create()) // For raw responses
        .addConverterFactory(GsonConverterFactory.create(gson)) // For JSON responses
        .build()

    val apiService: RetrofitAPICredentials = retrofit.create(RetrofitAPICredentials::class.java)
}
