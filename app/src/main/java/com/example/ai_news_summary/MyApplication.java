package com.example.ai_news_summary;

import android.app.Application;
import com.example.ai_news_summary.core.database.AppDatabase;

public class MyApplication extends Application {
    private static MyApplication instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}