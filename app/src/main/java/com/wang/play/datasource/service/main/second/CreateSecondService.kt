package com.wang.play.datasource.service.main.second

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CreateSecondService {

    private const val SECOND_BASE_URL = "https://pixabay.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    private val secondRetrofit = Retrofit.Builder()
        .baseUrl(SECOND_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    fun <T> testCreate(serviceClass: Class<T>): T = secondRetrofit.create(serviceClass)
    inline fun <reified T> testCreate(): T = testCreate(T::class.java)


}