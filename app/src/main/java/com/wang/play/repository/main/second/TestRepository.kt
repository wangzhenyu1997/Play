package com.wang.play.repository.main.second

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wang.play.data.test.Hit
import com.wang.play.datasource.service.test.TestService
import com.wang.play.repository.main.second.TestPagingSource
import kotlinx.coroutines.flow.Flow

class TestRepository(private val service: TestService) {

    fun getSecondResultStream(query: String): Flow<PagingData<Hit>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TestPagingSource(
                    service,
                    query
                )
            }
        ).flow
    }

}