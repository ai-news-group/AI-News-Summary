package com.example.ai_news_summary.model;

public class FolderItem {
    private String id;
    private String name;
    private int itemCount;
    private long createTime;

    public FolderItem(String id, String name, int itemCount, long createTime) {
        this.id = id;
        this.name = name;
        this.itemCount = itemCount;
        this.createTime = createTime;
    }

    public FolderItem(String id, String name, int itemCount) {
        this(id, name, itemCount, System.currentTimeMillis());
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getItemCount() { return itemCount; }
    public long getCreateTime() { return createTime; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
    public void setCreateTime(long createTime) { this.createTime = createTime; }
}
