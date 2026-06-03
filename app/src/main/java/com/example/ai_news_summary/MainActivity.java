package com.example.ai_news_summary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.feature.category.CategoryFragment;
import com.example.ai_news_summary.feature.home.HomeFragment;
import com.example.ai_news_summary.feature.mine.MineFragment;
import com.example.ai_news_summary.feature.recommend.RecommendFragment;
import com.example.ai_news_summary.feature.search.SearchFragment;
import com.example.ai_news_summary.models.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnHome, btnRecommend, btnCategory, btnSearch, btnMine;
    private FrameLayout fragmentContainer;
    private RecyclerView rvNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        btnHome = findViewById(R.id.btn_home);
        btnRecommend = findViewById(R.id.btn_recommend);
        btnCategory = findViewById(R.id.btn_category);
        btnSearch = findViewById(R.id.btn_search);
        btnMine = findViewById(R.id.btn_mine);
        fragmentContainer = findViewById(R.id.fragment_container);
        rvNewsList = findViewById(R.id.rv_news_list);

        // 默认显示推荐页（隐藏新闻列表）
        rvNewsList.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
        replaceFragment(new RecommendFragment());

        // 设置按钮点击事件
        btnHome.setOnClickListener(v -> {
            rvNewsList.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
            replaceFragment(new HomeFragment());
        });

        btnRecommend.setOnClickListener(v -> {
            rvNewsList.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
            replaceFragment(new RecommendFragment());
        });

        btnCategory.setOnClickListener(v -> {
            rvNewsList.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
            replaceFragment(new CategoryFragment());
        });

        btnSearch.setOnClickListener(v -> {
            rvNewsList.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
            replaceFragment(new SearchFragment());
        });

        btnMine.setOnClickListener(v -> {
            rvNewsList.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
            replaceFragment(new MineFragment());
        });

        // 其他功能按钮
        initOtherFeatures();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void initOtherFeatures() {
        EditText etSearch = findViewById(R.id.et_search);
        if (etSearch != null) {
            etSearch.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        Button btnHistory = findViewById(R.id.btn_history);
        if (btnHistory != null) {
            btnHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            });
        }

        Button btnSwitchToDevelop = findViewById(R.id.btn_switch_to_develop);
        if (btnSwitchToDevelop != null) {
            btnSwitchToDevelop.setOnClickListener(v -> {
                if (fragmentContainer.getVisibility() == View.VISIBLE) {
                    fragmentContainer.setVisibility(View.GONE);
                    rvNewsList.setVisibility(View.VISIBLE);
                    btnSwitchToDevelop.setText("切换到分类浏览模式");
                } else {
                    fragmentContainer.setVisibility(View.VISIBLE);
                    rvNewsList.setVisibility(View.GONE);
                    btnSwitchToDevelop.setText("切换到新闻列表模式");
                }
            });
        }
    }
}