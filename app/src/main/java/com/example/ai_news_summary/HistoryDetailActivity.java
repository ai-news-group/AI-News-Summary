package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ai_news_summary.data.dao.AppDatabase;
import com.example.ai_news_summary.core.model.History;

import java.util.concurrent.Executors;

public class HistoryDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvSource, tvReadTime, tvContent;
    private Button btnReadAgain, btnDelete;
    private History history;
    private int historyId;
    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        // 获取传入的历史记录ID
        historyId = getIntent().getIntExtra("history_id", -1);

        // 添加调试日志
        Toast.makeText(this, "收到的 history_id: " + historyId, Toast.LENGTH_LONG).show();

        if (historyId == -1) {
            Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupToolbar();
        loadHistoryData();
        setupListeners();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_title);
        tvSource = findViewById(R.id.tv_source);
        tvReadTime = findViewById(R.id.tv_read_time);
        tvContent = findViewById(R.id.tv_content);
        btnReadAgain = findViewById(R.id.btn_read_again);
        btnDelete = findViewById(R.id.btn_delete);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("历史详情");
    }

    private void loadHistoryData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            history = AppDatabase.getInstance(this).historyDao().getHistoryById(historyId);
            runOnUiThread(() -> {
                if (history == null) {
                    Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                displayData();
            });
        });
    }

    private void displayData() {
        tvTitle.setText(history.getTitle());
        tvSource.setText(history.getSource());
        tvReadTime.setText(history.getReadTime());

        // 模拟正文内容（实际应该从 News 表获取）
        String content = generateContent(history.getTitle());
        tvContent.setText(content);
    }

    private String generateContent(String title) {
        return title + "\n\n"
                + "随着人工智能技术的快速发展，新闻摘要系统成为了信息过载时代的解决方案。"
                + "通过先进的自然语言处理算法，系统能够自动提取文章的核心内容，"
                + "为用户提供简洁准确的摘要信息。\n\n"
                + "这种技术不仅提高了用户的阅读效率，还为用户提供了个性化的阅读体验。"
                + "未来，随着技术的不断进步，AI摘要系统将会更加智能和精准。\n\n"
                + "用户可以通过历史记录功能，随时回顾之前阅读过的新闻内容。"
                + "历史记录会保存用户的阅读轨迹，方便用户管理和查找感兴趣的文章。";
    }

    private void setupListeners() {
        btnReadAgain.setOnClickListener(v -> {
            // 重新阅读：跳转到新闻详情页
            Toast.makeText(this, "重新阅读：" + history.getTitle(), Toast.LENGTH_SHORT).show();
            // TODO: 跳转到新闻详情页
        });

        btnDelete.setOnClickListener(v -> {
            showDeleteDialog();
        });
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除这条历史记录吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    deleteHistory();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).historyDao().delete(history);
            runOnUiThread(() -> {
                Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}