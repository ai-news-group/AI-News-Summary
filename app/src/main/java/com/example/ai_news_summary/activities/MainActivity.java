package com.example.ai_news_summary.activities;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.adapters.NewsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // 检查控件是否找到
        if (viewPager == null) {
            Log.e(TAG, "viewPager is null! 请检查 activity_main.xml 中的 id");
            return;
        }
        if (tabLayout == null) {
            Log.e(TAG, "tabLayout is null! 请检查 activity_main.xml 中的 id");
            return;
        }

        // 设置适配器
        NewsPagerAdapter adapter = new NewsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // 关联 TabLayout 和 ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    String[] titles = {"头条", "科技", "体育", "娱乐", "财经"};
                    tab.setText(titles[position]);
                    Log.d(TAG, "设置标签 " + position + ": " + titles[position]);
                }
        ).attach();

        Log.d(TAG, "MainActivity 初始化完成");
    }
}