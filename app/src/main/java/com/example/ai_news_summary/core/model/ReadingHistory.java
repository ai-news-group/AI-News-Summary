package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "reading_history")
public class ReadingHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String newsId;
    private long readTime;
    private int readDuration;

    @Ignore
    public ReadingHistory() {}

    public ReadingHistory(String userId, String newsId, long readTime, int readDuration) {
        this.userId = userId;
        this.newsId = newsId;
        this.readTime = readTime;
        this.readDuration = readDuration;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getNewsId() { return newsId; }
    public void setNewsId(String newsId) { this.newsId = newsId; }
    public long getReadTime() { return readTime; }
    public void setReadTime(long readTime) { this.readTime = readTime; }
    public int getReadDuration() { return readDuration; }
    public void setReadDuration(int readDuration) { this.readDuration = readDuration; }
}