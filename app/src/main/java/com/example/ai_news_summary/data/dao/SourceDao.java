package com.example.ai_news_summary.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.ai_news_summary.core.model.Source;
import java.util.List;

@Dao
public interface SourceDao {

    @Insert
    void insert(Source source);

    @Query("SELECT * FROM Source")
    List<Source> getAllSources();

    @Query("SELECT * FROM Source WHERE name = :name")
    Source getSourceByName(String name);
}