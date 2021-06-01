package com.wang.play.ui.fragment.main.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.wang.play.data.test.Hit
import com.wang.play.data.test.UiModel
import com.wang.play.repository.test.TestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SecondViewModel(private val repository: TestRepository) : ViewModel() {

    class SecondViewModelFactory(private val repository: TestRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(TestRepository::class.java)
                .newInstance(repository)
        }
    }

    //当前所查询的目标
    private var currentQueryValue: String? = null

    //查询目标返回的数据
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null


    fun searchHit(query: String): Flow<PagingData<UiModel>> {

        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            //避免重复查询
            return lastResult
        }
        currentQueryValue = query


        //getTestResultStream返回值为 Flow<PagingData<Hit>>
        val newResult: Flow<PagingData<UiModel>> = repository.getTestResultStream(query)
            //返回Flow<PagingData<UiModel.HitItem>>
            .map { value: PagingData<Hit> -> value.map { UiModel.HitItem(it) } }
            //插入separator
            .map {
                //before:T? after:T?
                it.insertSeparators { before: UiModel.HitItem?, _: UiModel.HitItem? ->
                    if (before == null) {
                        return@insertSeparators UiModel.SeparatorItem("搜索结果:", true)
                    }
                    //在每个like超过1000的item下面添加separator
                    if (before.hit.likes > 1500) {
                        return@insertSeparators UiModel.SeparatorItem(
                            before.hit.likes.toString(),
                            false
                        )
                    } else {
                        return@insertSeparators null
                    }
                }
            }
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}


