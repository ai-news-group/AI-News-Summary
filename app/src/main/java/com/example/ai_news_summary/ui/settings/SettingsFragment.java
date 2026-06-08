package com.example.ai_news_summary.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.ai_news_summary.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Switch switchNotification = view.findViewById(R.id.switch_notification);
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), isChecked ? "通知已开启" : "通知已关闭", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.tv_night_mode).setOnClickListener(v -> {
            Toast.makeText(getContext(), "夜间模式开发中", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.tv_font_size).setOnClickListener(v -> {
            Toast.makeText(getContext(), "字体大小设置开发中", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.tv_clear_cache).setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("清除缓存")
                    .setMessage("确定要清除所有缓存吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        Toast.makeText(getContext(), "缓存已清除", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });

        view.findViewById(R.id.tv_about).setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("关于")
                    .setMessage("AI News Summary\n版本 1.0.0\n\n智能新闻摘要应用")
                    .setPositiveButton("确定", null)
                    .show();
        });

        view.findViewById(R.id.tv_logout).setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("退出登录")
                    .setMessage("确定要退出登录吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        Toast.makeText(getContext(), "已退出登录", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }
}