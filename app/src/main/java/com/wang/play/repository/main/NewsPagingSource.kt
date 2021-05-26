package com.wang.play.repository.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wang.play.MyApplication
import com.wang.play.data.main.second.News
import com.wang.play.datasource.service.main.NewsAPI


//Int表示页数的数据类型，New.Data表示每一项数据所对应的对象类型
class NewsPagingSource(private val newsApi: NewsAPI) : PagingSource<Int, News.Data>() {
    override fun getRefreshKey(state: PagingState<Int, News.Data>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News.Data> {
        return try {

            //key代表当前页数
            val page = params.key ?: 1
            //loadSize表示每一页的数据大小
            val pageSize = params.loadSize
            val response = newsApi.newsLoad(
                MyApplication.APP_KEY,
                page = page,
                page_size = pageSize,
                is_filter = 1
            )

            val item = response.result.data
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (item.isNotEmpty()) page + 1 else null
            LoadResult.Page(item, prevKey, nextKey)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}