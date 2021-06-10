package com.wang.play.repository.main.second

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wang.play.data.main.second.Hit
import com.wang.play.datasource.service.main.second.SecondService
import kotlinx.coroutines.flow.Flow

class SecondRepository(private val service: SecondService) {

    fun getSecondResultStream(query: String): Flow<PagingData<Hit>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SecondPagingSource(
                    service,
                    query
                )
            }
        ).flow
    }

}