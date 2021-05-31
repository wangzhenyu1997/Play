package com.wang.play.repository.test

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wang.play.data.test.Hit
import com.wang.play.datasource.service.test.TestService
import kotlinx.coroutines.flow.Flow

class TestRepository(private val service: TestService) {

    fun getTestResultStream(query: String): Flow<PagingData<Hit>> {
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