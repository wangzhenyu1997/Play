package com.wang.play.datasource.service

import com.wang.play.data.main.Beautiful
import retrofit2.http.GET
import retrofit2.http.Path

interface BeautifulAPI {

    //page: >=1
    //count: [10, 50]
    @GET("page/{page}/count/{count}")
    suspend fun beautifulLoad(@Path("page") page: String, @Path("count") count: String): Beautiful

}


