package com.example.ai_news_summary.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ai_news_summary.core.model.News;

import java.util.List;

@Dao
public interface NewsDao {

    // 获取所有新闻（给推荐页面用）
    @Query("SELECT * FROM news ORDER BY time DESC")
    LiveData<List<News>> getAllNews();

    // 插入单条新闻
    @Insert
    void insert(News news);

    // 插入多条新闻
    @Insert
    void insertAll(List<News> newsList);

    // 获取新闻数量
    @Query("SELECT COUNT(*) FROM news")
    int getCount();
}