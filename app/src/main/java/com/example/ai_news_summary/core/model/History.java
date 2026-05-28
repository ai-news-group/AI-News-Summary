package com.example.ai_news_summary.core.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "History")
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int newsId;           // 关联的新闻ID
    private String title;         // 新闻标题
    private String summary;       // 新闻摘要
    private String source;        // 新闻来源
    private String readTime;      // 阅读时间（显示用）
    private long timestamp;       // 时间戳（用于排序）
    private boolean isRead;       // 是否已读

    // 空构造函数（Room需要）
    public History() {
    }

    // 带参数的构造函数
    @Ignore
    public History(int newsId, String title, String summary, String source, String readTime, long timestamp) {
        this.newsId = newsId;
        this.title = title;
        this.summary = summary;
        this.source = source;
        this.readTime = readTime;
        this.timestamp = timestamp;
        this.isRead = true;
    }

    // Getter 和 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}