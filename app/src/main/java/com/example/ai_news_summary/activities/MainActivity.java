package com.example.ai_news_summary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.adapters.NewsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);  // 使用新的布局文件

        // 初始化控件
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        btnBackToMain = findViewById(R.id.btn_back_to_main);

        // 返回按钮：跳转回根目录的 MainActivity（你的界面）
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.ai_news_summary.MainActivity.class);
            startActivity(intent);
            finish();  // 关闭当前页面
        });

        // 设置 ViewPager2 适配器
        NewsPagerAdapter adapter = new NewsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // 关联 TabLayout 和 ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    String[] titles = {"头条", "科技", "体育", "娱乐", "财经"};
                    tab.setText(titles[position]);
                }
        ).attach();
    }
}