package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sources")
public class Source {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String url;
    private String category;
    private boolean isEnabled;
    private int newsCount;      // 添加新闻数量字段
    private int icon;           // 添加图标字段

    public Source() {
    }

    // Getter 和 Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    // 添加缺失的方法
    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}