package com.wang.play.repository.test

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wang.play.MyApplication
import com.wang.play.data.main.Beautiful
import com.wang.play.data.test.Hit
import com.wang.play.datasource.service.test.TestService

//Int为页码的数据类型
//Hit为每一页每一项的数据类型
//service为获取数据的Service,
class TestPagingSource(
    private val service: TestService,
    private val query: String,
    private val image_type: String = "all"
) : PagingSource<Int, Hit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {

        //获取当前页码
        val position = params.key ?: 1

        return try {
            //获取当前页的数据
            val response =
                service.searchImages(MyApplication.Pixabay_Key, query, image_type, position, params.loadSize)
            val repos = response.hits


            //最后一页数据是空的
            LoadResult.Page(
                data = repos,
                prevKey = if (position > 1) position - 1 else null,
                nextKey = if (repos.isEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    // refresh键用于初始加载后对PagingSource.load的后续刷新调用
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        // 我们需要获取最接近最近访问的索引的页面的上一个键（如果上一个为空，则为下一个键）。
        // 锚点位置是最近访问的索引

        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
