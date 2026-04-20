package com.example.ai_news_summary.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.ai_news_summary.fragments.*;

public class NewsPagerAdapter extends FragmentStateAdapter {

    public NewsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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