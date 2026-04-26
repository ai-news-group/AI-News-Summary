package com.example.ai_news_summary;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.ui.favorite.FavoriteAdapter;
import com.example.ai_news_summary.ui.favorite.FavoriteViewModel;
import com.example.ai_news_summary.model.FavoriteItem;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;
    private View bottomBar;
    private TextView tvSelectedCount;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("我的收藏");

        recyclerView = findViewById(R.id.recyclerView);
        bottomBar = findViewById(R.id.bottomBar);
        tvSelectedCount = findViewById(R.id.tvSelectedCount);
        btnDelete = findViewById(R.id.btnDelete);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        viewModel.getItems().observe(this, items -> {
            adapter.setItems(items);
            getSupportActionBar().setTitle("我的收藏 (" + (items == null ? 0 : items.size()) + ")");
        });

        viewModel.getIsBatchMode().observe(this, isBatchMode -> {
            adapter.setBatchMode(isBatchMode);
            bottomBar.setVisibility(isBatchMode ? View.VISIBLE : View.GONE);
        });

        adapter.setOnItemClickListener(item -> {
            viewModel.markAsRead(item.getId());
            // TODO: 跳转详情页
        });

        adapter.setOnItemSelectListener(count -> {
            viewModel.setSelectedCount(count);
            tvSelectedCount.setText("已选择 " + count + " 项");
        });

        btnDelete.setOnClickListener(v -> {
            List<String> ids = new ArrayList<>(adapter.getSelectedIds());
            viewModel.deleteSelected(ids);
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
        }
        return super.onOptionsItemSelected(item);
    }
}