package com.example.ai_news_summary.ui.profile;  // 根据你的实际包名调整

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.ai_news_summary.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        TextView tvUsername = view.findViewById(R.id.tv_username);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvBio = view.findViewById(R.id.tv_bio);

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tvUsername.setText(user.getUsername() != null ? user.getUsername() : "未登录");
                tvEmail.setText(user.getEmail() != null ? user.getEmail() : "暂无邮箱");
                tvBio.setText(user.getBio() != null ? user.getBio() : "这个人很懒，什么都没写");
            }
        });

        // 入口点击事件
        view.findViewById(R.id.btn_favorites).setOnClickListener(v -> {
            // TODO: 跳转到收藏页面
        });
        view.findViewById(R.id.btn_history).setOnClickListener(v -> {
            // TODO: 跳转到历史页面
        });
        view.findViewById(R.id.btn_settings).setOnClickListener(v -> {
            // TODO: 跳转到设置页面
        });
    }
}