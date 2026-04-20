package com.example.ai_news_summary.core.model;

public class Source {
    private String name;
    private String icon;
    private int newsCount;

    public Source(String name, String icon, int newsCount) {
        this.name = name;
        this.icon = icon;
        this.newsCount = newsCount;
    }

    public String getName() { return name; }
    public String getIcon() { return icon; }
    public int getNewsCount() { return newsCount; }
}