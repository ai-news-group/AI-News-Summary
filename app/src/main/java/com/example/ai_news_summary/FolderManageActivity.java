package com.example.ai_news_summary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.model.FolderItem;
import com.example.ai_news_summary.ui.favorite.FolderAdapter;
import com.example.ai_news_summary.ui.favorite.FolderViewModel;

public class FolderManageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FolderAdapter adapter;
    private FolderViewModel viewModel;
    private Button btnAddFolder;
    private TextView tvEmpty;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_manage);

        initViews();
        setupToolbar();
        setupViewModel();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        btnAddFolder = findViewById(R.id.btn_add_folder);
        tvEmpty = findViewById(R.id.tv_empty);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("收藏夹管理");
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(FolderViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FolderAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getFolders().observe(this, folders -> {
            adapter.setFolders(folders);
            if (folders == null || folders.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupListeners() {
        btnAddFolder.setOnClickListener(v -> showAddFolderDialog());

        adapter.setOnFolderActionListener(new FolderAdapter.OnFolderActionListener() {
            @Override
            public void onFolderClick(FolderItem folder) {
                // 点击收藏夹，可以跳转到该收藏夹的新闻列表
                Toast.makeText(FolderManageActivity.this,
                        "查看收藏夹: " + folder.getName(), Toast.LENGTH_SHORT).show();
                // 后续可以跳转到按收藏夹筛选的列表页
            }

            @Override
            public void onEditClick(FolderItem folder) {
                showEditFolderDialog(folder);
            }

            @Override
            public void onDeleteClick(FolderItem folder) {
                showDeleteConfirmDialog(folder);
            }
        });
    }

    private void showAddFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新建收藏夹");

        final EditText input = new EditText(this);
        input.setHint("请输入收藏夹名称");
        builder.setView(input);

        builder.setPositiveButton("创建", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                viewModel.addFolder(name);
                Toast.makeText(this, "收藏夹已创建", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showEditFolderDialog(FolderItem folder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑收藏夹");

        final EditText input = new EditText(this);
        input.setText(folder.getName());
        input.setSelection(input.getText().length());
        builder.setView(input);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                folder.setName(name);
                viewModel.updateFolder(folder);
                Toast.makeText(this, "已重命名", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showDeleteConfirmDialog(FolderItem folder) {
        new AlertDialog.Builder(this)
                .setTitle("删除收藏夹")
                .setMessage("确定要删除 \"" + folder.getName() + "\" 吗？\n其中的收藏将被移动到\"全部收藏\"。")
                .setPositiveButton("删除", (dialog, which) -> {
                    viewModel.deleteFolder(folder);
                    Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
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