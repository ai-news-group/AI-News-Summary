package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.core.model.News;  // ← 改这里
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import com.example.ai_news_summary.NewsDetailActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNewsList;
    private EditText etSearch;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvNewsList = findViewById(R.id.rv_news_list);
        etSearch = findViewById(R.id.et_search);

        rvNewsList.setLayoutManager(new LinearLayoutManager(this));

        loadMockData();

        newsAdapter = new NewsAdapter(newsList, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                // 跳转到详情页
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                intent.putExtra("news", news);  // 把新闻对象传过去
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
}