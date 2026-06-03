package com.example.ai_news_summary.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import com.example.ai_news_summary.core.model.History;
import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void insert(History history);

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    List<History> getAllHistory();

    @Query("SELECT * FROM History WHERE id = :id")
    History getHistoryById(int id);

    @Delete
    void delete(History history);

    @Query("DELETE FROM history WHERE id IN (:ids)")
    void deleteByIds(List<Integer> ids);

    @Query("DELETE FROM history")
    void clearAll();

    @Query("SELECT COUNT(*) FROM history WHERE newsId = :newsId")
    int isHistoryExists(int newsId);

    @Query("SELECT COUNT(*) FROM history")
    int getHistoryCount();

    @Query("DELETE FROM history WHERE newsId = :newsId")
    void deleteByNewsId(int newsId);
}