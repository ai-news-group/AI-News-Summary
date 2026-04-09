package com.example.ai_news_summary.core.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.annotation.NonNull;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String userId;
    private String phone;
    private String email;
    private String password;
    private String nickname;
    private String avatarUrl;
    private long registerTime;
    private long lastLoginTime;

    @Ignore
    public User() {}

    public User(@NonNull String userId, String phone, String email, String password,
                String nickname, long registerTime, long lastLoginTime, String avatarUrl) {
        this.userId = userId;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.avatarUrl = avatarUrl;
    }

    @NonNull
    public String getUserId() { return userId; }
    public void setUserId(@NonNull String userId) { this.userId = userId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public long getRegisterTime() { return registerTime; }
    public void setRegisterTime(long registerTime) { this.registerTime = registerTime; }
    public long getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(long lastLoginTime) { this.lastLoginTime = lastLoginTime; }
}