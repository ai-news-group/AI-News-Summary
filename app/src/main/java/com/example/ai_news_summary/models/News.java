package com.example.ai_news_summary.models;

import java.io.Serializable;  // 添加这行导入

public class News implements Serializable {  // 添加 implements Serializable
    private int id;
    private String title;
    private String description;
    private String content;
    private String imageUrl;
    private String author;
    private String date;
    private String category;
    private boolean isFavorite;

    public News(int id, String title, String description, String content,
                String imageUrl, String author, String date, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.imageUrl = imageUrl;
        this.author = author;
        this.date = date;
        this.category = category;
        this.isFavorite = false;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public boolean isFavorite() { return isFavorite; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setContent(String content) { this.content = content; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setAuthor(String author) { this.author = author; }
    public void setDate(String date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}