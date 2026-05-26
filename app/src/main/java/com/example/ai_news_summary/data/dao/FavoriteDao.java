package com.example.ai_news_summary.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import com.example.ai_news_summary.core.model.Favorite;
import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE userId = :userId")
    List<Favorite> getFavoritesByUserId(String userId);

    @Query("SELECT * FROM favorite WHERE userId = :userId AND newsId = :newsId")
    Favorite getFavoriteByUserIdAndNewsId(String userId, String newsId);

    @Query("DELETE FROM favorite WHERE userId = :userId AND newsId = :newsId")
    void deleteByUserIdAndNewsId(String userId, String newsId);

    @Query("DELETE FROM favorite WHERE userId = :userId")
    void deleteAllByUserId(String userId);
}