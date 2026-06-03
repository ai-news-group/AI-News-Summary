package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.models.News;
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

        // 历史记录按钮
        Button btnHistory = findViewById(R.id.btn_history);
        if (btnHistory != null) {
            btnHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            });
        }

        // 切换到 develop 分支界面的按钮
        Button btnSwitchToDevelop = findViewById(R.id.btn_switch_to_develop);
        if (btnSwitchToDevelop != null) {
            btnSwitchToDevelop.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, com.example.ai_news_summary.activities.MainActivity.class);
                startActivity(intent);
            });
        }

        // 搜索框
        EditText etSearch = findViewById(R.id.et_search);
        if (etSearch != null) {
            etSearch.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        rvNewsList = findViewById(R.id.rv_news_list);
        rvNewsList.setLayoutManager(new LinearLayoutManager(this));

        loadMockData();

        newsAdapter = new NewsAdapter(newsList, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                saveToHistory(news);
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
        newsList.add(new News(1, "AI技术最新进展",
                "人工智能在2026年取得了重大突破...",
                "详细内容：人工智能技术正在快速发展...",
                "https://picsum.photos/id/0/400/200",
                "科技日报", "今天 10:00", "科技"));

        newsList.add(new News(2, "Android 16发布",
                "谷歌发布了Android 16，带来全新特性...",
                "详细内容：Android 16带来了性能提升...",
                "https://picsum.photos/id/1/400/200",
                "IT之家", "昨天 15:30", "科技"));

        newsList.add(new News(3, "新闻摘要系统上线",
                "AI新闻摘要系统正式上线...",
                "详细内容：基于人工智能的新闻摘要系统...",
                "https://picsum.photos/id/2/400/200",
                "官方公告", "昨天 09:00", "科技"));

        newsList.add(new News(4, "5G应用场景拓展",
                "5G技术在各个领域的应用不断深化...",
                "详细内容：5G技术正在工业互联网...",
                "https://picsum.photos/id/3/400/200",
                "通信世界", "4月12日", "科技"));

        newsList.add(new News(5, "智能家居新趋势",
                "AI让家居生活更智能更便捷...",
                "详细内容：智能音箱、智能照明...",
                "https://picsum.photos/id/4/400/200",
                "科技前沿", "4月11日", "科技"));
    }

    private void saveToHistory(News news) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                int exists = AppDatabase.getInstance(this).historyDao().isHistoryExists(news.getId());
                if (exists == 0) {
                    History history = new History(
                            news.getId(),
                            news.getTitle(),
                            news.getDescription(),
                            news.getAuthor(),
                            getCurrentTimeString(),
                            System.currentTimeMillis()
                    );
                    AppDatabase.getInstance(this).historyDao().insert(history);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String getCurrentTimeString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd HH:mm", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
}