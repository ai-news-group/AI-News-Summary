package com.example.ai_news_summary.feature.mine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ai_news_summary.core.database.AppDatabase
import com.example.ai_news_summary.core.model.News

class MineViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val newsDao = db.newsDao()

    val recommendNews: LiveData<List<News>> = newsDao.getAllNews()

    fun saveFeedback(newsId: Int, type: String) {
        // 先空实现，避免构造参数错误
        // 后续根据 RecommendFeedback 的真实构造再修改
    }
}