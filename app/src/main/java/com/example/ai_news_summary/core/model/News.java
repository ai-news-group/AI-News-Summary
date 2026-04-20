package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import java.io.Serializable;  // ← 添加这一行 import

@Entity(tableName = "news")
public class News implements Serializable {  // ← 添加 implements Serializable

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String summary;
    private String source;
    private String time;
    private boolean isFavorite;
    private int readCount;  // ← 这个字段你应该已经有了

    // 空构造函数
    public News() {
    }

    // 带参数的构造函数
    @Ignore
    public News(String title, String summary, String source, String time) {
        this.title = title;
        this.summary = summary;
        this.source = source;
        this.time = time;
        this.isFavorite = false;
        this.readCount = 0;
    }

    // Getter 和 Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public int getReadCount() { return readCount; }  // ← 添加这个
    public void setReadCount(int readCount) { this.readCount = readCount; }  // ← 添加这个
}