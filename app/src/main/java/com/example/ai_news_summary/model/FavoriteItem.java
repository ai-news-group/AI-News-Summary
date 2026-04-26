package com.example.ai_news_summary.model;

public class FavoriteItem {
    private String id;
    private String title;
    private String summary;
    private String source;
    private long timestamp;
    private boolean isRead;
    private boolean isPinned;

    public FavoriteItem(String id, String title, String summary, String source, long timestamp, boolean isRead, boolean isPinned) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.source = source;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.isPinned = isPinned;
    }

    public FavoriteItem(String id, String title, String summary, String source, long timestamp, boolean isRead) {
        this(id, title, summary, source, timestamp, isRead, false);
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getSource() { return source; }
    public long getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }
    public boolean isPinned() { return isPinned; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setSource(String source) { this.source = source; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setRead(boolean read) { isRead = read; }
    public void setPinned(boolean pinned) { isPinned = pinned; }
}
