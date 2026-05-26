package com.example.ai_news_summary.feature.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai_news_summary.data.dao.RecommendFeedbackDao
import kotlinx.coroutines.launch

class RecommendViewModel(
    private val newsDao: NewsDao,
    private val feedbackDao: RecommendFeedbackDao
) : ViewModel() {

    private val _recommendList = MutableLiveData<List<News>>()
    val recommendList: LiveData<List<News>> = _recommendList

    val feedbackSuccess = MutableLiveData<Boolean>()

    fun refreshRecommend() {
        viewModelScope.launch {
            val allNews = newsDao.getAllNews()
            val readIds = newsDao.getReadIds()
            val dislikeIds = feedbackDao.getDislikeNewsIds()

            val result = allNews
                .filter { it.id !in readIds }
                .filter { it.id !in dislikeIds }
                .take(20)

            _recommendList.postValue(result)
        }
    }

    fun submitFeedback(newsId: Long, type: String) {
        viewModelScope.launch {
            feedbackDao.insertFeedback(newsId, type)
            feedbackSuccess.postValue(true)
            refreshRecommend()
        }
    }
}