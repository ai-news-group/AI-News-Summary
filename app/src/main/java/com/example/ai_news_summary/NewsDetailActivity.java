package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ai_news_summary.core.model.News;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvSource, tvTime, tvSummary;
    private Button btnBack;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // 获取传递过来的新闻数据
        news = (News) getIntent().getSerializableExtra("news");

        // 初始化控件
        tvTitle = findViewById(R.id.tv_detail_title);
        tvSource = findViewById(R.id.tv_detail_source);
        tvTime = findViewById(R.id.tv_detail_time);
        tvSummary = findViewById(R.id.tv_detail_summary);
        btnBack = findViewById(R.id.btn_back);

        // 显示数据
        if (news != null) {
            tvTitle.setText(news.getTitle());
            tvSource.setText(news.getSource());
            tvTime.setText(news.getTime());
            tvSummary.setText(news.getSummary());
        }

        // 返回按钮
        btnBack.setOnClickListener(v -> finish());
    }
}