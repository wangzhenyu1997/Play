package com.wang.play.datasource.service

import com.wang.play.data.main.second.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    //key 请求Key：9a721292358a94f0c92caba0b3064cb5
    //type string 非必须
    //支持类型top(推荐,默认)guonei(国内)guoji(国际)yule(娱乐)tiyu(体育)junshi(军事)keji(科技)caijing(财经)shishang(时尚)youxi(游戏)qiche(汽车)jiankang(健康)
    //page int 非必须	当前页数, 默认1, 最大50
    //page_size	int	非必须	每页返回条数, 默认30 , 最大30
    //is_filter	int	非必须	是否只返回有内容详情的新闻, 1:是, 默认0
    @GET("toutiao/index")
    suspend fun newsLoad(
        @Query("key") key: String,
        @Query("type") type: String = "top",
        @Query("page") page: Int = 1,
        @Query("page_size") page_size: Int = 30,
        @Query("is_filter") is_filter: Int = 0,
    ): News

}
