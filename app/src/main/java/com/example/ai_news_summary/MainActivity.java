package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.core.model.News;
import com.example.ai_news_summary.data.dao.AppDatabase;
import com.example.ai_news_summary.core.model.History;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNewsList;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 导航图标
        ImageView ivSearch = findViewById(R.id.iv_search);
        ImageView ivSource = findViewById(R.id.iv_source);
        ImageView ivSettings = findViewById(R.id.iv_settings);

        ivSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        ivSource.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SourceListActivity.class);
            startActivity(intent);
        });

        ivSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SummarySettingsActivity.class);
            startActivity(intent);
        });

        // ========== 添加历史记录按钮 ==========
        android.widget.Button btnHistory = findViewById(R.id.btn_history);
        if (btnHistory != null) {
            btnHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            });
        } else {
            android.util.Log.e("MainActivity", "btn_history not found");
        }
        // ===================================

        rvNewsList = findViewById(R.id.rv_news_list);
        rvNewsList.setLayoutManager(new LinearLayoutManager(this));

        loadMockData();

        newsAdapter = new NewsAdapter(newsList, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                // ========== 保存到历史记录 ==========
                saveToHistory(news);
                // =================================

                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(News news, int position) {
                news.setFavorite(!news.isFavorite());
                newsAdapter.notifyItemChanged(position);
                String msg = news.isFavorite() ? "已收藏" : "已取消收藏";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        rvNewsList.setAdapter(newsAdapter);
    }

    private void loadMockData() {
        newsList.add(new News("AI技术最新进展", "人工智能在2026年取得了重大突破...", "科技日报", "今天 10:00"));
        newsList.add(new News("Android 16发布", "谷歌发布了Android 16，带来全新特性...", "IT之家", "昨天 15:30"));
        newsList.add(new News("新闻摘要系统上线", "AI新闻摘要系统正式上线，为用户提供个性化推荐...", "官方公告", "昨天 09:00"));
        newsList.add(new News("5G应用场景拓展", "5G技术在各个领域的应用不断深化...", "通信世界", "4月12日"));
        newsList.add(new News("智能家居新趋势", "AI让家居生活更智能更便捷...", "科技前沿", "4月11日"));
    }

    // ========== 添加保存历史记录的方法 ==========
    private void saveToHistory(News news) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // 检查是否已存在
                int exists = AppDatabase.getInstance(this).historyDao().isHistoryExists(news.getId());
                if (exists == 0) {
                    // 不存在则添加
                    History history = new History(
                            news.getId(),
                            news.getTitle(),
                            news.getSummary(),
                            news.getSource(),
                            getCurrentTimeString(),
                            System.currentTimeMillis()
                    );
                    AppDatabase.getInstance(this).historyDao().insert(history);
                    android.util.Log.d("MainActivity", "已保存到历史记录: " + news.getTitle());
                }
            } catch (Exception e) {
                android.util.Log.e("MainActivity", "保存历史记录失败: " + e.getMessage());
            }
        });
    }

    private String getCurrentTimeString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd HH:mm", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
    // ==========================================
}