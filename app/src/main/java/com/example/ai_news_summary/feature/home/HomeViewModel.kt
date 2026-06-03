package com.example.ai_news_summary.feature.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.data.dao.AppDatabase

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val newsDao = db.newsDao()

    val newsList: LiveData<List<News>> = newsDao.getAllNews()
}