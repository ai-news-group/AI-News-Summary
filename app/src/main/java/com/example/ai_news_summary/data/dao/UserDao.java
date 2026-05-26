package com.example.ai_news_summary.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.ai_news_summary.core.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update  // ← 添加这个
    void update(User user);  // ← 添加这个方法

    @Query("SELECT * FROM user WHERE userId = :userId")
    User getUserById(String userId);

    @Query("SELECT * FROM user LIMIT 1")
    User getFirstUser();

    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);
}