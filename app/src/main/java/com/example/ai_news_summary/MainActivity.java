package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.ai_news_summary.feature.recommend.RecommendFragment;
import com.example.ai_news_summary.feature.home.HomeFragment;
import com.example.ai_news_summary.feature.category.CategoryFragment;
import com.example.ai_news_summary.feature.search.SearchFragment;
import com.example.ai_news_summary.feature.mine.MineFragment;

public class MainActivity extends AppCompatActivity {

    private Button btnHome, btnRecommend, btnCategory, btnSearch, btnMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = findViewById(R.id.btn_home);
        btnRecommend = findViewById(R.id.btn_recommend);
        btnCategory = findViewById(R.id.btn_category);
        btnSearch = findViewById(R.id.btn_search);
        btnMine = findViewById(R.id.btn_mine);

        // 默认显示推荐页
        replaceFragment(new RecommendFragment());

        btnHome.setOnClickListener(v -> replaceFragment(new HomeFragment()));
        btnRecommend.setOnClickListener(v -> replaceFragment(new RecommendFragment()));
        btnCategory.setOnClickListener(v -> replaceFragment(new CategoryFragment()));
        btnSearch.setOnClickListener(v -> replaceFragment(new SearchFragment()));
        btnMine.setOnClickListener(v -> replaceFragment(new MineFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}