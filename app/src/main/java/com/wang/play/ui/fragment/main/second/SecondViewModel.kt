package com.wang.play.ui.fragment.main.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wang.play.data.main.second.News
import com.wang.play.repository.main.CreateNewsService
import kotlinx.coroutines.flow.Flow

class SecondViewModel : ViewModel() {



    fun getNews(): Flow<PagingData<News.Data>> {
        return CreateNewsService.getPagingData().cachedIn(viewModelScope)
    }
}


