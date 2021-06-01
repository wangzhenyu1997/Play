package com.wang.play.datasource.service.test

import com.wang.play.data.test.Pixabay
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {


    //key为我的KEY
    //q  A URL encoded search term. If omitted,
    // all images are returned. This value may not exceed 100 characters.
    //image_type 按图像类型筛选结果 接受值：“all”、“photo”、“illustration”、“vector”
    //默认值：“all”
    //order  How the results should be ordered. Accepted values: "popular", "latest"
    //Default: "popular"
    //page页码
    //per_page 每页的结果数，默认20   Accepted values: 3 - 200
    @GET("api/")
    suspend fun searchImages(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("image_type") image_type: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Pixabay



}
