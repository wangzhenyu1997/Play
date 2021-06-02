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


    fun searchHit(query: String): Flow<PagingData<UiModel>> {

        //getTestResultStream返回值为 Flow<PagingData<Hit>>
        return repository.getTestResultStream(query)
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


    }

}


