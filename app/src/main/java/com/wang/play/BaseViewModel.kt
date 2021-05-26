package com.wang.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val error = MutableLiveData<Exception?>().apply {
        value = null
    }

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    //从网络加载数据的方法
    fun launchData(
        block: suspend () -> Unit,
        error: suspend (Exception) -> Unit,
        complete: suspend () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO)
        {
            try {
                block()
            } catch (e: Exception) {
                error(e)
            } finally {
                complete()
            }

        }
    }

}

