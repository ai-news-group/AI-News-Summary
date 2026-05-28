package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.adapter.HistoryAdapter;
import com.example.ai_news_summary.data.dao.AppDatabase;
import com.example.ai_news_summary.core.model.History;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistoryList;
    private TextView tvHistoryCount, tvClearAll, tvEditMode, tvSelectedCount, tvViewStats;  // ← 添加 tvViewStats
    private LinearLayout llEmpty, llDeleteBar;
    private CheckBox cbSelectAll;
    private Button btnDeleteSelected;
    private HistoryAdapter adapter;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadHistoryData();
        setupListeners();
    }

    private void initViews() {
        rvHistoryList = findViewById(R.id.rv_history_list);
        tvHistoryCount = findViewById(R.id.tv_history_count);
        tvClearAll = findViewById(R.id.tv_clear_all);
        tvEditMode = findViewById(R.id.tv_edit_mode);
        tvSelectedCount = findViewById(R.id.tv_selected_count);
        llEmpty = findViewById(R.id.ll_empty);
        llDeleteBar = findViewById(R.id.ll_delete_bar);
        cbSelectAll = findViewById(R.id.cb_select_all);
        btnDeleteSelected = findViewById(R.id.btn_delete_selected);
        tvViewStats = findViewById(R.id.tv_view_stats);  // ← 添加这行
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(null, new HistoryAdapter.OnHistoryClickListener() {
            @Override
            public void onHistoryClick(History history) {
                // 添加调试日志
                Toast.makeText(HistoryActivity.this, "点击: " + history.getTitle() + ", ID: " + history.getId(), Toast.LENGTH_LONG).show();

                // 跳转到历史详情页
                Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                intent.putExtra("history_id", history.getId());
                startActivity(intent);
            }

            @Override
            public void onSelectionChanged(int selectedCount) {
                updateSelectedUI(selectedCount);
            }
        });
        rvHistoryList.setAdapter(adapter);
    }

    private void loadHistoryData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            historyList = AppDatabase.getInstance(this).historyDao().getAllHistory();
            runOnUiThread(this::updateUI);
        });
    }

    private void updateUI() {
        if (historyList == null || historyList.isEmpty()) {
            llEmpty.setVisibility(View.VISIBLE);
            rvHistoryList.setVisibility(View.GONE);
            tvHistoryCount.setText("共 0 条记录");
        } else {
            llEmpty.setVisibility(View.GONE);
            rvHistoryList.setVisibility(View.VISIBLE);
            tvHistoryCount.setText(String.format("共 %d 条记录", historyList.size()));
            adapter.updateList(historyList);
        }
    }

    private void setupListeners() {
        tvClearAll.setOnClickListener(v -> {
            if (historyList != null && !historyList.isEmpty()) {
                showClearAllDialog();
            }
        });

        tvEditMode.setOnClickListener(v -> {
            if (adapter.isEditMode()) {
                exitEditMode();
            } else {
                enterEditMode();
            }
        });

        cbSelectAll.setOnClickListener(v -> {
            if (cbSelectAll.isChecked()) {
                adapter.selectAll();
            } else {
                adapter.clearSelection();
            }
        });

        btnDeleteSelected.setOnClickListener(v -> deleteSelectedHistories());

        // ← 添加统计入口点击事件
        tvViewStats.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, HistoryStatsActivity.class);
            startActivity(intent);
        });
    }

    private void enterEditMode() {
        adapter.setEditMode(true);
        llDeleteBar.setVisibility(View.VISIBLE);
        tvEditMode.setText("取消");
        tvClearAll.setVisibility(View.GONE);
        updateSelectedUI(0);
    }

    private void exitEditMode() {
        adapter.setEditMode(false);
        llDeleteBar.setVisibility(View.GONE);
        tvEditMode.setText("编辑");
        tvClearAll.setVisibility(View.VISIBLE);
        cbSelectAll.setChecked(false);
    }

    private void updateSelectedUI(int selectedCount) {
        tvSelectedCount.setText(String.format("已选择 %d 条", selectedCount));
        btnDeleteSelected.setEnabled(selectedCount > 0);
        btnDeleteSelected.setAlpha(selectedCount > 0 ? 1.0f : 0.5f);

        if (adapter.getItemCount() > 0) {
            cbSelectAll.setChecked(selectedCount == adapter.getItemCount());
            cbSelectAll.setEnabled(adapter.getItemCount() > 0);
        }
    }

    private void deleteSelectedHistories() {
        List<History> selectedHistories = adapter.getSelectedHistories();
        if (selectedHistories.isEmpty()) return;

        Executors.newSingleThreadExecutor().execute(() -> {
            for (History history : selectedHistories) {
                AppDatabase.getInstance(this).historyDao().delete(history);
            }
            historyList = AppDatabase.getInstance(this).historyDao().getAllHistory();
            runOnUiThread(() -> {
                updateUI();
                if (adapter.isEditMode()) {
                    exitEditMode();
                }
                Toast.makeText(this, String.format("已删除 %d 条记录", selectedHistories.size()), Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void showClearAllDialog() {
        new AlertDialog.Builder(this)
                .setTitle("清空全部记录")
                .setMessage("确定要清空所有浏览历史吗？此操作不可恢复。")
                .setPositiveButton("确定", (dialog, which) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase.getInstance(this).historyDao().clearAll();
                        historyList = AppDatabase.getInstance(this).historyDao().getAllHistory();
                        runOnUiThread(() -> {
                            updateUI();
                            if (adapter.isEditMode()) {
                                exitEditMode();
                            }
                            Toast.makeText(this, "已清空所有历史记录", Toast.LENGTH_SHORT).show();
                        });
                    });
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (adapter != null && adapter.isEditMode()) {
            exitEditMode();
        } else {
            super.onBackPressed();
        }
    }
}