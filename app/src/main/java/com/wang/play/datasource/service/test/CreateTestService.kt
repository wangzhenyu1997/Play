package com.wang.play.datasource.service.test

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CreateTestService {

    private const val TEST_BASE_URL = "https://pixabay.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    private val testRetrofit = Retrofit.Builder()
        .baseUrl(TEST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    fun <T> testCreate(serviceClass: Class<T>): T = testRetrofit.create(serviceClass)
    inline fun <reified T> testCreate(): T = testCreate(T::class.java)


}