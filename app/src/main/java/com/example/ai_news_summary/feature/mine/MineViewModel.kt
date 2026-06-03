package com.example.ai_news_summary.feature.mine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.data.dao.AppDatabase

class MineViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val newsDao = db.newsDao()

    // 临时使用 MutableLiveData，避免调用不存在的方法
    private val _favoriteNews = MutableLiveData<List<News>>()
    val favoriteNews: LiveData<List<News>> = _favoriteNews

    init {
        // 加载收藏新闻（需要 NewsDao 中有 getFavoriteNews 方法）
        // 暂时注释，等后续添加
        // _favoriteNews.value = newsDao.getFavoriteNews()

        // 临时使用空列表
        _favoriteNews.value = emptyList()
    }
}