package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.annotation.NonNull;

@Entity(tableName = "news")
public class News {
    @PrimaryKey
    @NonNull
    private String newsId;
    private String title;
    private String content;
    private String summary;
    private String imageUrl;
    private String category;
    private String source;
    private long publishTime;
    private int viewCount;
    private int likeCount;

    @Ignore
    public News() {}

    public News(@NonNull String newsId, String title, String content, String imageUrl,
                String category, String source, long publishTime,
                String summary, int viewCount, int likeCount) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.category = category;
        this.source = source;
        this.publishTime = publishTime;
        this.summary = summary;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    @NonNull
    public String getNewsId() { return newsId; }
    public void setNewsId(@NonNull String newsId) { this.newsId = newsId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public long getPublishTime() { return publishTime; }
    public void setPublishTime(long publishTime) { this.publishTime = publishTime; }
    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
}