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
        // 加载所有新闻（可以从数据库或网络获取）
        allNews.add(new News("AI技术最新进展", "人工智能在2026年取得了重大突破...", "科技日报", "今天 10:00"));
        allNews.add(new News("Android 16发布", "谷歌发布了Android 16...", "IT之家", "昨天 15:30"));
        allNews.add(new News("新闻摘要系统上线", "AI新闻摘要系统正式上线...", "官方公告", "昨天 09:00"));
        allNews.add(new News("5G应用场景拓展", "5G技术在各个领域的应用...", "通信世界", "4月12日"));
        allNews.add(new News("智能家居新趋势", "AI让家居生活更智能...", "科技前沿", "4月11日"));
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
            if (news.getTitle().toLowerCase().contains(keyword) ||
                    news.getSummary().toLowerCase().contains(keyword)) {
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