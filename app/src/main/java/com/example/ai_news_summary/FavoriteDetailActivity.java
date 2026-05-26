package com.example.ai_news_summary;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.example.ai_news_summary.model.FavoriteItem;
import com.example.ai_news_summary.ui.favorite.FavoriteViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavoriteDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvSource, tvTime, tvSummary, tvNote, tvPosition;
    private Button btnReadLink, btnDelete, btnPrev, btnNext, btnEditNote;
    private Toolbar toolbar;

    private FavoriteViewModel viewModel;
    private List<FavoriteItem> items;
    private int currentPosition = 0;
    private String currentNote = "";

    // 用于存储笔记的键（实际项目中应该用数据库）
    private static final String NOTE_PREFIX = "note_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);

        initViews();
        setupToolbar();
        setupViewModel();
        getIntentData();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tv_title);
        tvSource = findViewById(R.id.tv_source);
        tvTime = findViewById(R.id.tv_time);
        tvSummary = findViewById(R.id.tv_summary);
        tvNote = findViewById(R.id.tv_note);
        tvPosition = findViewById(R.id.tv_position);
        btnReadLink = findViewById(R.id.btn_read_link);
        btnDelete = findViewById(R.id.btn_delete);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        btnEditNote = findViewById(R.id.btn_edit_note);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("新闻详情");
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        viewModel.getItems().observe(this, items -> {
            if (items != null) {
                this.items = items;
                updateUI();
            }
        });
    }

    private void getIntentData() {
        String itemId = getIntent().getStringExtra("item_id");
        currentPosition = getIntent().getIntExtra("position", 0);

        if (itemId != null && viewModel.getItems().getValue() != null) {
            List<FavoriteItem> itemList = viewModel.getItems().getValue();
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getId().equals(itemId)) {
                    currentPosition = i;
                    break;
                }
            }
        }
    }

    private void updateUI() {
        if (items == null || items.isEmpty() || currentPosition >= items.size()) {
            finish();
            return;
        }

        FavoriteItem item = items.get(currentPosition);

        // 标记为已读
        if (!item.isRead()) {
            viewModel.markAsRead(item.getId());
        }

        // 显示内容
        tvTitle.setText(item.getTitle());
        tvSource.setText(item.getSource());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        tvTime.setText(sdf.format(new Date(item.getTimestamp())));
        tvSummary.setText(item.getSummary());

        // 加载笔记
        loadNote(item.getId());

        // 更新位置显示
        tvPosition.setText((currentPosition + 1) + " / " + items.size());

        // 更新按钮状态
        updateButtonState();
        setupButtonListeners(item);
    }

    private void loadNote(String itemId) {
        // 使用 SharedPreferences 存储笔记（简化版，实际应用应使用数据库）
        android.content.SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);
        currentNote = prefs.getString(NOTE_PREFIX + itemId, "");
        if (currentNote.isEmpty()) {
            tvNote.setText("暂无笔记，点击编辑添加");
            tvNote.setTextColor(getColor(android.R.color.darker_gray));
        } else {
            tvNote.setText(currentNote);
            tvNote.setTextColor(getColor(android.R.color.black));
        }
    }

    private void saveNote(String itemId, String note) {
        android.content.SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);
        prefs.edit().putString(NOTE_PREFIX + itemId, note).apply();
        currentNote = note;
        if (note.isEmpty()) {
            tvNote.setText("暂无笔记，点击编辑添加");
            tvNote.setTextColor(getColor(android.R.color.darker_gray));
        } else {
            tvNote.setText(note);
            tvNote.setTextColor(getColor(android.R.color.black));
        }
    }

    private void updateButtonState() {
        btnPrev.setEnabled(currentPosition > 0);
        btnNext.setEnabled(currentPosition < items.size() - 1);
    }

    private void setupButtonListeners(FavoriteItem item) {
        // 阅读原文
        btnReadLink.setOnClickListener(v -> {
            // 实际项目中应该使用 item.getLink()
            String url = "https://www.example.com/news/" + item.getId();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        // 删除
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("删除确认")
                    .setMessage("确定要删除这条收藏吗？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        viewModel.deleteSelected(java.util.Collections.singletonList(item.getId()));
                        finish();
                        Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });

        // 上一页
        btnPrev.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                updateUI();
            }
        });

        // 下一页
        btnNext.setOnClickListener(v -> {
            if (currentPosition < items.size() - 1) {
                currentPosition++;
                updateUI();
            }
        });

        // 编辑笔记
        btnEditNote.setOnClickListener(v -> showEditNoteDialog(item.getId()));
    }

    private void showEditNoteDialog(String itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_note, null);
        EditText etNote = view.findViewById(R.id.et_note);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnSave = view.findViewById(R.id.btn_save);

        etNote.setText(currentNote);

        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String note = etNote.getText().toString();
            saveNote(itemId, note);
            dialog.dismiss();
            Toast.makeText(this, "笔记已保存", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}