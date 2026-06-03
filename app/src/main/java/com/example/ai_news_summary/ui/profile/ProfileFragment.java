package com.example.ai_news_summary.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.ai_news_summary.EditProfileActivity;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.SettingsActivity;

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
                tvUsername.setText(user.getNickname() != null ? user.getNickname() : "未登录");
                tvEmail.setText(user.getEmail() != null ? user.getEmail() : "暂无邮箱");
                tvBio.setVisibility(View.GONE);
            }
        });

        // 点击头像进入编辑资料
        view.findViewById(R.id.iv_avatar).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        // 点击昵称进入编辑资料
        view.findViewById(R.id.tv_username).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        // 设置
        view.findViewById(R.id.btn_settings).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });
    }
}