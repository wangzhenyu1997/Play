package com.wang.play.repository.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wang.play.data.main.second.News
import com.wang.play.datasource.service.main.NewsAPI
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CreateNewsService {

    private const val BASE_URL = "http://v.juhe.cn/"

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    private inline fun <reified T> create(): T = create(T::class.java)

    private val newsApi = create<NewsAPI>()

    fun getPagingData(): Flow<PagingData<News.Data>> {
        return Pager(config = PagingConfig(30), pagingSourceFactory = {
            NewsPagingSource(newsApi)
        }).flow
    }

}