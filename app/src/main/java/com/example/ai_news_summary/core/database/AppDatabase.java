package com.example.ai_news_summary.core.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.ai_news_summary.core.model.*;  // ← 改这里
import com.example.ai_news_summary.data.dao.*;
import com.example.ai_news_summary.core.model.News;

@Database(
        entities = {
                User.class,
                UserInterest.class,
                News.class,
                ReadingHistory.class,
                Favorite.class,
                SearchHistory.class,
                RecommendFeedback.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract UserDao userDao();
    public abstract UserInterestDao userInterestDao();
    public abstract NewsDao newsDao();
    public abstract ReadingHistoryDao readingHistoryDao();
    public abstract FavoriteDao favoriteDao();
    public abstract SearchHistoryDao searchHistoryDao();
    public abstract RecommendFeedbackDao recommendFeedbackDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "news_app.db"
                    ).build();
                }
            }
        }
        return instance;
    }
}