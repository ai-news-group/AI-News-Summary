package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String newsId;
    private long favoriteTime;

    @Ignore
    public Favorite() {}

    public Favorite(String userId, String newsId, long favoriteTime) {
        this.userId = userId;
        this.newsId = newsId;
        this.favoriteTime = favoriteTime;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getNewsId() { return newsId; }
    public void setNewsId(String newsId) { this.newsId = newsId; }
    public long getFavoriteTime() { return favoriteTime; }
    public void setFavoriteTime(long favoriteTime) { this.favoriteTime = favoriteTime; }
}