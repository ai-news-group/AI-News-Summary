package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.SourceAdapter;
import com.example.ai_news_summary.core.model.Source;
import java.util.ArrayList;
import java.util.List;

public class SourceListActivity extends AppCompatActivity {

    private RecyclerView rvSourceList;
    private SourceAdapter adapter;
    private List<Source> sourceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_list);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvSourceList = findViewById(R.id.rv_source_list);
        rvSourceList.setLayoutManager(new LinearLayoutManager(this));

        loadSources();
        setupAdapter();
    }

    private void loadSources() {
        sourceList.add(new Source("科技日报", "🔬", 45));
        sourceList.add(new Source("IT之家", "💻", 38));
        sourceList.add(new Source("通信世界", "📡", 22));
        sourceList.add(new Source("科技前沿", "🚀", 31));
        sourceList.add(new Source("官方公告", "📢", 15));
        sourceList.add(new Source("人工智能", "🤖", 28));
    }

    private void setupAdapter() {
        adapter = new SourceAdapter(sourceList, source -> {
            Toast.makeText(this, "查看" + source.getName() + "的新闻", Toast.LENGTH_SHORT).show();
            // TODO: 后续创建 SourceNewsActivity 后再取消注释
        });
        rvSourceList.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}