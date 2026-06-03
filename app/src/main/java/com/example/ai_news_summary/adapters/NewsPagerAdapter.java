package com.example.ai_news_summary.adapters;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.ai_news_summary.fragments.*;

public class NewsPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "NewsPagerAdapter";

    public NewsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        Log.d(TAG, "NewsPagerAdapter 创建");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "createFragment 位置: " + position);
        switch (position) {
            case 0:
                return new HeadlineFragment();
            case 1:
                return new TechnologyFragment();
            case 2:
                return new SportsFragment();
            case 3:
                return new EntertainmentFragment();
            case 4:
                return new FinanceFragment();
            default:
                return new HeadlineFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}