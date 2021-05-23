package com.wang.play.ui.fragment.main.second

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.data.main.second.News
import com.wang.play.datasource.room.entity.User
import com.wang.play.datasource.service.NewsAPI
import com.wang.play.repository.CreateNewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class SecondViewModel : ViewModel() {

    private val _news = MutableLiveData<News?>().apply {
        value = null
    }
    val news: LiveData<News?> = _news

    fun loadNews(
        key: String,
        type: String = "top",
        page: Int = 1,
        page_size: Int = 30,
        is_filter: Int = 0,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val temp: News = CreateNewsService.create<NewsAPI>()
                    .newsLoad(key, type, page, page_size, is_filter)
                _news.postValue(temp)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        MyApplication.context,
                        "请检查网络连接",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }


}