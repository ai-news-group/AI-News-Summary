package com.example.ai_news_summary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.core.model.News;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private Button btnSearch;
    private RecyclerView rvSearchResults;
    private TextView tvEmpty;
    private NewsAdapter adapter;
    private List<News> allNews = new ArrayList<>();
    private List<News> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        loadAllNews();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        etSearchInput = findViewById(R.id.et_search_input);
        btnSearch = findViewById(R.id.btn_search);
        rvSearchResults = findViewById(R.id.rv_search_results);
        tvEmpty = findViewById(R.id.tv_empty);
    }

    private void loadAllNews() {
        // 修复：使用正确的 News 构造函数 (title, desc, time)
        News news1 = new News("AI技术最新进展", "人工智能在2026年取得了重大突破...", "今天 10:00");
        news1.setSource("科技日报");
        allNews.add(news1);

        News news2 = new News("Android 16发布", "谷歌发布了Android 16...", "昨天 15:30");
        news2.setSource("IT之家");
        allNews.add(news2);

        News news3 = new News("新闻摘要系统上线", "AI新闻摘要系统正式上线...", "昨天 09:00");
        news3.setSource("官方公告");
        allNews.add(news3);

        News news4 = new News("5G应用场景拓展", "5G技术在各个领域的应用...", "4月12日");
        news4.setSource("通信世界");
        allNews.add(news4);

        News news5 = new News("智能家居新趋势", "AI让家居生活更智能...", "4月11日");
        news5.setSource("科技前沿");
        allNews.add(news5);
    }

    private void setupRecyclerView() {
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(searchResults, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                Intent intent = new Intent(SearchActivity.this, NewsDetailActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(News news, int position) {
                news.setFavorite(!news.isFavorite());
                adapter.notifyItemChanged(position);
            }
        });
        rvSearchResults.setAdapter(adapter);
    }

    private void setupListeners() {
        btnSearch.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String keyword = etSearchInput.getText().toString().trim().toLowerCase();
        searchResults.clear();

        if (keyword.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvSearchResults.setVisibility(View.GONE);
            return;
        }

        for (News news : allNews) {
            String title = news.getTitle() != null ? news.getTitle().toLowerCase() : "";
            String summary = news.getSummary() != null ? news.getSummary().toLowerCase() : "";
            if (title.contains(keyword) || summary.contains(keyword)) {
                searchResults.add(news);
            }
        }

        if (searchResults.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvSearchResults.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvSearchResults.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
}