package com.example.ai_news_summary.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.models.News;
import com.google.gson.Gson;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView ivNewsImage;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvDate;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initViews();
        loadNewsData();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        ivNewsImage = findViewById(R.id.ivNewsImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDate = findViewById(R.id.tvDate);
        tvContent = findViewById(R.id.tvContent);
    }

    private void loadNewsData() {
        String newsJson = getIntent().getStringExtra("news");
        if (newsJson != null) {
            News news = new Gson().fromJson(newsJson, News.class);

            tvTitle.setText(news.getTitle());
            tvAuthor.setText(news.getAuthor());
            tvDate.setText(news.getDate());
            tvContent.setText(news.getContent());

            if (news.getImageUrl() != null && !news.getImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(news.getImageUrl())
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(ivNewsImage);
            }
        } else {
            Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}