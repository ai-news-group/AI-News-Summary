package com.example.ai_news_summary.data.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.ai_news_summary.core.model.User;
import com.example.ai_news_summary.core.model.UserInterest;
import com.example.ai_news_summary.core.model.News;
import com.example.ai_news_summary.core.model.ReadingHistory;
import com.example.ai_news_summary.core.model.Favorite;
import com.example.ai_news_summary.core.model.SearchHistory;
import com.example.ai_news_summary.core.model.RecommendFeedback;
import com.example.ai_news_summary.core.model.History;
import com.example.ai_news_summary.core.model.Source;

@Database(
        entities = {
                User.class,
                UserInterest.class,
                News.class,
                ReadingHistory.class,
                Favorite.class,
                SearchHistory.class,
                RecommendFeedback.class,
                History.class,
                Source.class
        },
        version = 5,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    // DAO 方法（都在同一个包下，不需要 import）
    public abstract UserDao userDao();
    public abstract UserInterestDao userInterestDao();
    public abstract NewsDao newsDao();
    public abstract ReadingHistoryDao readingHistoryDao();
    public abstract FavoriteDao favoriteDao();
    public abstract SearchHistoryDao searchHistoryDao();
    public abstract RecommendFeedbackDao recommendFeedbackDao();
    public abstract HistoryDao historyDao();
    // 如果有 SourceDao 也需要加上

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "news_app.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}