package com.example.ai_news_summary.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import com.example.ai_news_summary.core.model.ReadingHistory;
import java.util.List;

@Dao
public interface ReadingHistoryDao {

    @Insert
    void insert(ReadingHistory history);

    @Delete
    void delete(ReadingHistory history);

    @Query("SELECT * FROM reading_history WHERE userId = :userId ORDER BY readTime DESC")
    List<ReadingHistory> getHistoryByUserId(String userId);

    @Query("SELECT * FROM reading_history WHERE userId = :userId AND newsId = :newsId")
    ReadingHistory getHistoryByUserIdAndNewsId(String userId, String newsId);

    @Query("DELETE FROM reading_history WHERE userId = :userId AND newsId = :newsId")
    void deleteByUserIdAndNewsId(String userId, String newsId);

    @Query("DELETE FROM reading_history WHERE userId = :userId")
    void deleteAllByUserId(String userId);
}