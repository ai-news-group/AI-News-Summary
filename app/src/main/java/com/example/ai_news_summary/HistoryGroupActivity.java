package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.HistoryGroupAdapter;
import com.example.ai_news_summary.data.dao.AppDatabase;
import com.example.ai_news_summary.core.model.History;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class HistoryGroupActivity extends AppCompatActivity {

    private RecyclerView rvGroupList;
    private TextView tvTotalCount, tvBackToList;
    private LinearLayout llEmpty;
    private HistoryGroupAdapter adapter;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_group);

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadHistoryData();
        setupListeners();
    }

    private void initViews() {
        rvGroupList = findViewById(R.id.rv_group_list);
        tvTotalCount = findViewById(R.id.tv_total_count);
        tvBackToList = findViewById(R.id.tv_back_to_list);
        llEmpty = findViewById(R.id.ll_empty);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        rvGroupList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryGroupAdapter(history -> {
            // 添加调试日志
            android.util.Log.d("HistoryGroup", "跳转到详情页，ID: " + history.getId() + ", 标题: " + history.getTitle());
            Toast.makeText(HistoryGroupActivity.this, "跳转到: " + history.getTitle(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HistoryGroupActivity.this, HistoryDetailActivity.class);
            intent.putExtra("history_id", history.getId());
            startActivity(intent);
        });
        rvGroupList.setAdapter(adapter);
    }

    private void loadHistoryData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            historyList = AppDatabase.getInstance(this).historyDao().getAllHistory();
            runOnUiThread(() -> {
                if (historyList == null || historyList.isEmpty()) {
                    llEmpty.setVisibility(android.view.View.VISIBLE);
                    rvGroupList.setVisibility(android.view.View.GONE);
                    tvTotalCount.setText("共 0 条记录");
                } else {
                    llEmpty.setVisibility(android.view.View.GONE);
                    rvGroupList.setVisibility(android.view.View.VISIBLE);
                    tvTotalCount.setText("共 " + historyList.size() + " 条记录");
                    displayGroupedData();
                }
            });
        });
    }

    private void displayGroupedData() {
        // 按日期分组
        Map<String, List<History>> groupedMap = new HashMap<>();

        for (History history : historyList) {
            String dateKey = getDateGroup(history.getTimestamp());
            if (!groupedMap.containsKey(dateKey)) {
                groupedMap.put(dateKey, new ArrayList<>());
            }
            groupedMap.get(dateKey).add(history);
        }

        // 转换为适配器需要的数据结构
        List<HistoryGroupAdapter.GroupItem> groups = new ArrayList<>();

        // 按顺序添加（今天、昨天、更早）
        String[] order = {"今天", "昨天", "更早"};
        for (String key : order) {
            if (groupedMap.containsKey(key)) {
                groups.add(new HistoryGroupAdapter.GroupItem(key, groupedMap.get(key)));
            }
        }

        adapter.setData(groups);
    }

    private String getDateGroup(long timestamp) {
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        Calendar recordTime = Calendar.getInstance();
        recordTime.setTimeInMillis(timestamp);

        // 比较日期（忽略时间）
        if (isSameDay(recordTime, today)) {
            return "今天";
        } else if (isSameDay(recordTime, yesterday)) {
            return "昨天";
        } else {
            return "更早";
        }
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private void setupListeners() {
        tvBackToList.setOnClickListener(v -> {
            finish(); // 返回普通列表页面
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}