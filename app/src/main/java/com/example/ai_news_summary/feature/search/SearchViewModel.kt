package com.example.ai_news_summary.feature.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.data.dao.AppDatabase

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val newsDao = db.newsDao()

    private val _searchResults = MutableLiveData<List<News>>()
    val searchResults: LiveData<List<News>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchNews(keyword: String) {
        _isLoading.value = true
        // 搜索逻辑 - 暂时使用空实现
        _searchResults.value = emptyList()
        _isLoading.value = false
    }

    fun clearResults() {
        _searchResults.value = emptyList()
    }
}