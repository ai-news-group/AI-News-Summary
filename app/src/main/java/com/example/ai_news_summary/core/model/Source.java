package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import java.io.Serializable;

@Entity(tableName = "sources")
public class Source implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String url;
    private String category;
    private boolean isEnabled;
    private int newsCount;
    private int icon;

    // Room 使用的默认构造函数
    public Source() {
    }

    // 添加 @Ignore 注解，让 Room 忽略这个构造函数
    @Ignore
    public Source(String name, String url, String category) {
        this.name = name;
        this.url = url;
        this.category = category;
        this.isEnabled = true;
    }

    // Getters
    public long getId() { return id; }
    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getCategory() { return category; }
    public boolean isEnabled() { return isEnabled; }
    public int getNewsCount() { return newsCount; }
    public int getIcon() { return icon; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUrl(String url) { this.url = url; }
    public void setCategory(String category) { this.category = category; }
    public void setEnabled(boolean enabled) { isEnabled = enabled; }
    public void setNewsCount(int newsCount) { this.newsCount = newsCount; }
    public void setIcon(int icon) { this.icon = icon; }
}