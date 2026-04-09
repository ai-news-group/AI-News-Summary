package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "recommend_feedback")
public class RecommendFeedback {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String newsId;
    private int feedbackType;
    private long feedbackTime;

    @Ignore
    public RecommendFeedback() {}

    public RecommendFeedback(String userId, String newsId, int feedbackType, long feedbackTime) {
        this.userId = userId;
        this.newsId = newsId;
        this.feedbackType = feedbackType;
        this.feedbackTime = feedbackTime;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getNewsId() { return newsId; }
    public void setNewsId(String newsId) { this.newsId = newsId; }
    public int getFeedbackType() { return feedbackType; }
    public void setFeedbackType(int feedbackType) { this.feedbackType = feedbackType; }
    public long getFeedbackTime() { return feedbackTime; }
    public void setFeedbackTime(long feedbackTime) { this.feedbackTime = feedbackTime; }
}