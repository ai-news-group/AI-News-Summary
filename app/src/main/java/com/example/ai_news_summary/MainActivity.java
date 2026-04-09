package com.example.ai_news_summary;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);

        TextView titleText = new TextView(this);
        titleText.setText("AI 新闻摘要系统");
        titleText.setTextSize(24);
        titleText.setTextColor(Color.parseColor("#2196F3"));
        titleText.setGravity(Gravity.CENTER);

        TextView successText = new TextView(this);
        successText.setText("数据库框架搭建成功！");
        successText.setTextSize(16);
        successText.setGravity(Gravity.CENTER);
        successText.setPadding(0, 48, 0, 48);

        TextView tableText = new TextView(this);
        tableText.setText("已创建的7张表：\n\n用户表\n新闻表\n兴趣表\n阅读历史表\n收藏表\n搜索历史表\n推荐反馈表");
        tableText.setTextSize(14);
        tableText.setGravity(Gravity.CENTER);

        layout.addView(titleText);
        layout.addView(successText);
        layout.addView(tableText);

        setContentView(layout);
    }
}