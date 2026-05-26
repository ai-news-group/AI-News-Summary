package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.model.FavoriteItem;
import com.example.ai_news_summary.ui.favorite.FavoriteAdapter;
import com.example.ai_news_summary.ui.favorite.FavoriteViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;
    private View bottomBar;
    private TextView tvSelectedCount;
    private Button btnDelete;
    private LinearLayout tagContainer;
    private Toolbar toolbar;

    // 标签列表
    private String[] hotTags = {"GPT", "Gemini", "大模型", "AI安全", "Google", "OpenAI"};
    private String currentSelectedTag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupViewModel();
        setupTagContainer();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        bottomBar = findViewById(R.id.bottomBar);
        tvSelectedCount = findViewById(R.id.tvSelectedCount);
        btnDelete = findViewById(R.id.btnDelete);
        tagContainer = findViewById(R.id.tag_container);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("我的收藏");
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        viewModel.getItems().observe(this, items -> {
            if (currentSelectedTag == null) {
                // 无筛选，显示全部
                adapter.setItems(items);
                updateTitle(items == null ? 0 : items.size());
            } else {
                // 有筛选，显示筛选后的结果
                filterByTag(currentSelectedTag);
            }
        });
    }

    private void setupTagContainer() {
        for (String tag : hotTags) {
            TextView tagView = createTagButton(tag);
            tagContainer.addView(tagView);
        }
    }

    private TextView createTagButton(String tag) {
        TextView tagView = new TextView(this);
        tagView.setText(tag);
        tagView.setPadding(40, 12, 40, 12);
        tagView.setBackgroundResource(R.drawable.tag_button_bg);
        tagView.setTextColor(getColor(R.color.black));
        tagView.setTextSize(13);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMarginEnd(12);
        tagView.setLayoutParams(params);

        tagView.setOnClickListener(v -> {
            if (currentSelectedTag != null && currentSelectedTag.equals(tag)) {
                // 已选中，点击取消筛选
                currentSelectedTag = null;
                resetTagButtonStyle(tagView, false);
                // 显示全部数据
                List<FavoriteItem> allItems = viewModel.getItems().getValue();
                adapter.setItems(allItems);
                updateTitle(allItems == null ? 0 : allItems.size());
            } else {
                // 清除其他标签的高亮
                clearAllTagSelection();
                // 高亮当前标签
                currentSelectedTag = tag;
                tagView.setBackgroundResource(R.drawable.tag_button_selected_bg);
                tagView.setTextColor(getColor(android.R.color.white));
                // 执行筛选
                filterByTag(tag);
            }
        });

        return tagView;
    }

    private void clearAllTagSelection() {
        for (int i = 0; i < tagContainer.getChildCount(); i++) {
            View child = tagContainer.getChildAt(i);
            if (child instanceof TextView) {
                TextView tagView = (TextView) child;
                tagView.setBackgroundResource(R.drawable.tag_button_bg);
                tagView.setTextColor(getColor(R.color.black));
            }
        }
    }

    private void resetTagButtonStyle(TextView tagView, boolean isSelected) {
        if (isSelected) {
            tagView.setBackgroundResource(R.drawable.tag_button_selected_bg);
            tagView.setTextColor(getColor(android.R.color.white));
        } else {
            tagView.setBackgroundResource(R.drawable.tag_button_bg);
            tagView.setTextColor(getColor(R.color.black));
        }
    }

    private void filterByTag(String tag) {
        List<FavoriteItem> allItems = viewModel.getItems().getValue();
        if (allItems != null && !allItems.isEmpty()) {
            List<FavoriteItem> filteredList = new ArrayList<>();
            for (FavoriteItem item : allItems) {
                if (item.getTitle().contains(tag) || item.getSummary().contains(tag)) {
                    filteredList.add(item);
                }
            }
            adapter.setItems(filteredList);
            updateTitle(filteredList.size(), tag);

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "没有包含 \"" + tag + "\" 的收藏", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateTitle(int count) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("我的收藏 (" + count + ")");
        }
    }

    private void updateTitle(int count, String tag) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("标签: " + tag + " (" + count + ")");
        }
    }

    private void setupListeners() {
        adapter.setOnItemClickListener(item -> {
            viewModel.markAsRead(item.getId());
            // 跳转详情页
            Intent intent = new Intent(MainActivity.this, FavoriteDetailActivity.class);
            intent.putExtra("item_id", item.getId());
            startActivity(intent);
        });

        adapter.setOnItemSelectListener(count -> {
            viewModel.setSelectedCount(count);
            tvSelectedCount.setText("已选择 " + count + " 项");
        });

        btnDelete.setOnClickListener(v -> {
            List<String> ids = new ArrayList<>(adapter.getSelectedIds());
            viewModel.deleteSelected(ids);
            if (currentSelectedTag != null) {
                // 删除后重新筛选
                filterByTag(currentSelectedTag);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_batch) {
            viewModel.toggleBatchMode();
            return true;
        } else if (id == R.id.action_folder) {
            Intent intent = new Intent(MainActivity.this, FolderManageActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}