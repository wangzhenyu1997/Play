package com.wang.play.datasource.service

import com.wang.play.data.main.testResponse
import retrofit2.http.GET


interface TestAPI {
    @GET("project/tree/json")
    suspend fun testLoad(): testResponse
}

