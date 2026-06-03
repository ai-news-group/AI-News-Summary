package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ai_news_summary.models.News;  // ← 改成这个

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvSource = findViewById(R.id.tv_detail_source);
        TextView tvTime = findViewById(R.id.tv_detail_time);
        TextView tvSummary = findViewById(R.id.tv_detail_summary);
        Button btnBack = findViewById(R.id.btn_back);

        // 获取传入的新闻对象 - 类型改为 models.News
        News news = (News) getIntent().getSerializableExtra("news");

        if (news != null) {
            tvTitle.setText(news.getTitle());
            tvSource.setText(news.getAuthor());      // models.News 用 getAuthor()
            tvTime.setText(news.getDate());          // models.News 用 getDate()
            tvSummary.setText(news.getDescription()); // models.News 用 getDescription()
        }

        btnBack.setOnClickListener(v -> finish());
    }
}