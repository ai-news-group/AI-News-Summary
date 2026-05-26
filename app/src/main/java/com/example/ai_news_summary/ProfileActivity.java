package com.example.ai_news_summary;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.ai_news_summary.ui.profile.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 显示个人中心 Fragment
        Fragment profileFragment = new ProfileFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_container, profileFragment)
                .commit();
    }
}