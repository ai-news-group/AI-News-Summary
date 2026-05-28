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
        // 修复：使用无参构造函数，然后 setter 方法设置值
        Source source1 = new Source();
        source1.setName("科技日报");
        source1.setNewsCount(45);
        source1.setIcon(android.R.drawable.ic_menu_info_details);
        sourceList.add(source1);

        Source source2 = new Source();
        source2.setName("IT之家");
        source2.setNewsCount(38);
        source2.setIcon(android.R.drawable.ic_menu_edit);
        sourceList.add(source2);

        Source source3 = new Source();
        source3.setName("通信世界");
        source3.setNewsCount(22);
        source3.setIcon(android.R.drawable.ic_menu_view);
        sourceList.add(source3);

        Source source4 = new Source();
        source4.setName("科技前沿");
        source4.setNewsCount(31);
        source4.setIcon(android.R.drawable.ic_menu_camera);
        sourceList.add(source4);

        Source source5 = new Source();
        source5.setName("官方公告");
        source5.setNewsCount(15);
        source5.setIcon(android.R.drawable.ic_menu_agenda);
        sourceList.add(source5);

        Source source6 = new Source();
        source6.setName("人工智能");
        source6.setNewsCount(28);
        source6.setIcon(android.R.drawable.ic_menu_share);
        sourceList.add(source6);
    }

    private void setupAdapter() {
        adapter = new SourceAdapter(sourceList, source -> {
            Toast.makeText(this, "查看" + source.getName() + "的新闻", Toast.LENGTH_SHORT).show();
        });
        rvSourceList.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}