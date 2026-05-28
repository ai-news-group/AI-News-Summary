package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import java.io.Serializable;  // 添加这个 import

@Entity(tableName = "news")
public class News implements Serializable {  // 实现 Serializable 接口
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String desc;
    private String time;
    private String content;
    private String summary;
    private String category;
    private String source;
    private long publishTime;
    private String imageUrl;
    private boolean isRecommended;
    private int readCount;
    private int likeCount;
    private boolean isFavorite;

    // Room 使用的默认构造函数
    public News() {
    }

    // 添加 @Ignore 注解，让 Room 忽略这个构造函数
    @Ignore
    public News(String title, String desc, String time) {
        this.title = title;
        this.desc = desc;
        this.time = time;
    }

    // Getter 和 Setter 方法
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}