package com.wang.play.ui.fragment.main.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wang.play.data.test.Hit
import com.wang.play.repository.test.TestRepository
import kotlinx.coroutines.flow.Flow

class SecondViewModel(private val repository: TestRepository) : ViewModel() {

    class SecondViewModelFactory(private val repository: TestRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(TestRepository::class.java)
                .newInstance(repository)
        }
    }


    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Hit>>? = null

    fun searchHit(query: String): Flow<PagingData<Hit>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Hit>> = repository.getTestResultStream(query)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}


