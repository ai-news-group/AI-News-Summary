package com.example.ai_news_summary.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.ai_news_summary.core.model.News;
import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void insert(News news);

    @Query("SELECT * FROM news WHERE id = :newsId")
    News getNewsById(int newsId);

    @Query("SELECT * FROM news WHERE id IN (:newsIds)")
    List<News> getNewsByIds(List<Integer> newsIds);
}