package com.example.ai_news_summary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.models.News;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private Button btnSearch;
    private RecyclerView rvSearchResults;
    private TextView tvEmpty;
    private NewsAdapter adapter;
    private final List<News> allNews = new ArrayList<>();  // 添加 final
    private final List<News> searchResults = new ArrayList<>();  // 添加 final

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
        allNews.add(new News(1, "AI技术最新进展",
                "人工智能在2026年取得了重大突破...",
                "详细内容：人工智能技术正在快速发展，深度学习、大语言模型等技术不断突破...",
                "https://picsum.photos/id/0/400/200",
                "科技日报", "今天 10:00", "科技"));

        allNews.add(new News(2, "Android 16发布",
                "谷歌发布了Android 16...",
                "详细内容：Android 16带来了性能提升、隐私保护增强等多项新功能...",
                "https://picsum.photos/id/1/400/200",
                "IT之家", "昨天 15:30", "科技"));

        allNews.add(new News(3, "新闻摘要系统上线",
                "AI新闻摘要系统正式上线...",
                "详细内容：基于人工智能的新闻摘要系统能够自动提取新闻要点...",
                "https://picsum.photos/id/2/400/200",
                "官方公告", "昨天 09:00", "科技"));

        allNews.add(new News(4, "5G应用场景拓展",
                "5G技术在各个领域的应用...",
                "详细内容：5G技术正在工业互联网、远程医疗、自动驾驶等领域广泛应用...",
                "https://picsum.photos/id/3/400/200",
                "通信世界", "4月12日", "科技"));

        allNews.add(new News(5, "智能家居新趋势",
                "AI让家居生活更智能...",
                "详细内容：智能音箱、智能照明、智能安防等设备正在普及...",
                "https://picsum.photos/id/4/400/200",
                "科技前沿", "4月11日", "科技"));
    }

    private void setupRecyclerView() {
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(searchResults, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                Intent intent = new Intent(SearchActivity.this, NewsDetailActivity.class);
                intent.putExtra("news", news);  // 现在可以正常传递了
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(News news, int position) {
                news.setFavorite(!news.isFavorite());
                adapter.notifyItemChanged(position);
                String msg = news.isFavorite() ? "已收藏" : "已取消收藏";
                Toast.makeText(SearchActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                    news.getDescription().toLowerCase().contains(keyword)) {
                searchResults.add(news);
            }
        }

        if (searchResults.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvSearchResults.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvSearchResults.setVisibility(View.VISIBLE);
            adapter.updateList(searchResults);
        }
    }
}