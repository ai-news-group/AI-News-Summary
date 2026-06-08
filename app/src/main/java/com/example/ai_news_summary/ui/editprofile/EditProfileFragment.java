package com.example.ai_news_summary.ui.editprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.ai_news_summary.R;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel viewModel;
    private EditText etNickname, etPhone, etEmail;
    private TextView tvSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        etNickname = view.findViewById(R.id.et_nickname);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        tvSave = view.findViewById(R.id.tv_save);

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                etNickname.setText(user.getNickname());
                etPhone.setText(user.getPhone());
                etEmail.setText(user.getEmail());
            }
        });

        tvSave.setOnClickListener(v -> {
            String nickname = etNickname.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nickname.isEmpty()) {
                Toast.makeText(getContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.updateUserProfile(nickname, phone, email);
            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        });
    }
}