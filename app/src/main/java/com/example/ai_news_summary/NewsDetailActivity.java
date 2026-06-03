package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ai_news_summary.models.News;

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
        Button btnFavorite = findViewById(R.id.btn_favorite);
        Button btnShare = findViewById(R.id.btn_share);

        News news = (News) getIntent().getSerializableExtra("news");

        if (news != null) {
            tvTitle.setText(news.getTitle());
            tvSource.setText(news.getAuthor());
            tvTime.setText(news.getDate());
            tvSummary.setText(news.getDescription());
        }

        btnBack.setOnClickListener(v -> finish());
        btnFavorite.setOnClickListener(v -> Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show());
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, tvTitle.getText().toString());
            startActivity(Intent.createChooser(shareIntent, "分享"));
        });
    }
}