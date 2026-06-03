package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;  // ← 添加这行

@Entity(tableName = "Source")
public class Source {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String icon;
    private int newsCount;

    // 空构造函数（Room 需要）
    public Source() {
    }

    // 带参数的构造函数 - 添加 @Ignore
    @Ignore
    public Source(String name, String icon, int newsCount) {
        this.name = name;
        this.icon = icon;
        this.newsCount = newsCount;
    }

    // Getter 和 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }
}