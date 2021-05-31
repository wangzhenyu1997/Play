package com.wang.play.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object CreateService {




    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()



    //test1
    private const val BASE_URL = "https://www.wanandroid.com/"

    //test2
    private const val BASE_URL2 = "https://gank.io/api/v2/data/category/Girl/type/Girl/"


    //test1
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    //test2
    private val retrofit2 = Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    //test1
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)

    //test2
    fun <T> create2(serviceClass: Class<T>): T = retrofit2.create(serviceClass)
    inline fun <reified T> create2(): T = create2(T::class.java)

}