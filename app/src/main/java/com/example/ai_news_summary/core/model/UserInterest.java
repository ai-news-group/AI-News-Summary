package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "user_interest")
public class UserInterest {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String category;
    private int weight;

    @Ignore
    public UserInterest() {}

    public UserInterest(String userId, String category, int weight) {
        this.userId = userId;
        this.category = category;
        this.weight = weight;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
}