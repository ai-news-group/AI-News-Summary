package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ai_news_summary.data.dao.AppDatabase;
import com.example.ai_news_summary.core.model.History;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class HistoryStatsActivity extends AppCompatActivity {

    private TextView tvTotalCount, tvTodayCount, tvWeekCount;
    private LinearLayout llTopSources, llTrend;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_stats);

        initViews();
        setupToolbar();
        loadStatsData();
    }

    private void initViews() {
        tvTotalCount = findViewById(R.id.tv_total_count);
        tvTodayCount = findViewById(R.id.tv_today_count);
        tvWeekCount = findViewById(R.id.tv_week_count);
        llTopSources = findViewById(R.id.ll_top_sources);
        llTrend = findViewById(R.id.ll_trend);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadStatsData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            historyList = AppDatabase.getInstance(this).historyDao().getAllHistory();
            runOnUiThread(() -> {
                if (historyList != null) {
                    displayStats();
                }
            });
        });
    }

    private void displayStats() {
        // 总阅读数
        tvTotalCount.setText(String.valueOf(historyList.size()));

        // 今日阅读数
        tvTodayCount.setText(String.valueOf(getTodayCount()));

        // 本周阅读数
        tvWeekCount.setText(String.valueOf(getWeekCount()));

        // 最常阅读来源
        displayTopSources();

        // 阅读趋势
        displayTrend();
    }

    private int getTodayCount() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        long startOfDay = todayStart.getTimeInMillis();

        int count = 0;
        for (History history : historyList) {
            if (history.getTimestamp() >= startOfDay) {
                count++;
            }
        }
        return count;
    }

    private int getWeekCount() {
        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.DAY_OF_WEEK, weekStart.getFirstDayOfWeek());
        weekStart.set(Calendar.HOUR_OF_DAY, 0);
        weekStart.set(Calendar.MINUTE, 0);
        weekStart.set(Calendar.SECOND, 0);
        weekStart.set(Calendar.MILLISECOND, 0);
        long startOfWeek = weekStart.getTimeInMillis();

        int count = 0;
        for (History history : historyList) {
            if (history.getTimestamp() >= startOfWeek) {
                count++;
            }
        }
        return count;
    }

    private void displayTopSources() {
        // 统计各来源数量
        Map<String, Integer> sourceCount = new HashMap<>();
        for (History history : historyList) {
            String source = history.getSource();
            sourceCount.put(source, sourceCount.getOrDefault(source, 0) + 1);
        }

        // 排序
        List<Map.Entry<String, Integer>> sorted = new java.util.ArrayList<>(sourceCount.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        llTopSources.removeAllViews();
        int maxCount = Math.min(5, sorted.size());

        if (maxCount == 0) {
            TextView emptyView = new TextView(this);
            emptyView.setText("暂无数据");
            emptyView.setTextSize(14);
            emptyView.setTextColor(0xFF999999);
            emptyView.setPadding(0, 16, 0, 16);
            llTopSources.addView(emptyView);
            return;
        }

        for (int i = 0; i < maxCount; i++) {
            Map.Entry<String, Integer> entry = sorted.get(i);
            LinearLayout item = createSourceItem(entry.getKey(), entry.getValue(), i + 1);
            llTopSources.addView(item);
        }
    }

    private LinearLayout createSourceItem(String source, int count, int rank) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setPadding(0, 12, 0, 12);
        item.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // 排名
        TextView tvRank = new TextView(this);
        tvRank.setText(rank + ".");
        tvRank.setTextSize(14);
        tvRank.setTextColor(0xFF6200EE);
        tvRank.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));

        // 来源名称
        TextView tvSource = new TextView(this);
        tvSource.setText(source);
        tvSource.setTextSize(14);
        tvSource.setTextColor(0xFF333333);
        tvSource.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f));

        // 阅读次数
        TextView tvCount = new TextView(this);
        tvCount.setText(count + "次");
        tvCount.setTextSize(14);
        tvCount.setTextColor(0xFF666666);
        tvCount.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        item.addView(tvRank);
        item.addView(tvSource);
        item.addView(tvCount);

        return item;
    }

    private void displayTrend() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());

        llTrend.removeAllViews();
        llTrend.setWeightSum(7);

        // 获取最近7天的数据
        for (int i = 6; i >= 0; i--) {
            Calendar day = Calendar.getInstance();
            day.add(Calendar.DAY_OF_YEAR, -i);
            day.set(Calendar.HOUR_OF_DAY, 0);
            day.set(Calendar.MINUTE, 0);
            day.set(Calendar.SECOND, 0);
            day.set(Calendar.MILLISECOND, 0);
            long startOfDay = day.getTimeInMillis();
            day.add(Calendar.DAY_OF_YEAR, 1);
            long endOfDay = day.getTimeInMillis();

            // 统计当天阅读数
            int count = 0;
            for (History history : historyList) {
                long timestamp = history.getTimestamp();
                if (timestamp >= startOfDay && timestamp < endOfDay) {
                    count++;
                }
            }

            // 创建柱状图
            LinearLayout barContainer = new LinearLayout(this);
            barContainer.setOrientation(LinearLayout.VERTICAL);
            barContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            barContainer.setGravity(android.view.Gravity.CENTER_HORIZONTAL);

            // 数量文字
            TextView tvCount = new TextView(this);
            tvCount.setText(String.valueOf(count));
            tvCount.setTextSize(10);
            tvCount.setTextColor(0xFF666666);
            tvCount.setPadding(0, 0, 0, 4);

            // 柱状图
            android.view.View bar = new android.view.View(this);
            int maxHeight = 80;
            int height = count > 0 ? Math.min(maxHeight, 20 + count * 10) : 4;
            bar.setLayoutParams(new LinearLayout.LayoutParams(32, height));
            bar.setBackgroundColor(0xFF6200EE);

            // 日期
            TextView tvDate = new TextView(this);
            tvDate.setText(sdf.format(day.getTime()));
            tvDate.setTextSize(10);
            tvDate.setTextColor(0xFF999999);
            tvDate.setPadding(0, 8, 0, 0);

            barContainer.addView(tvCount);
            barContainer.addView(bar);
            barContainer.addView(tvDate);
            llTrend.addView(barContainer);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}