package com.example.ai_news_summary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.adapter.NewsAdapter;
import com.example.ai_news_summary.models.News;
import java.util.ArrayList;
import java.util.List;

public class FinanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private TextView tvShanghai, tvShenzhen, tvChuangye;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShanghai = view.findViewById(R.id.tvShanghai);
        tvShenzhen = view.findViewById(R.id.tvShenzhen);
        tvChuangye = view.findViewById(R.id.tvChuangye);

        updateStockIndices();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsAdapter(new ArrayList<>(), new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                Toast.makeText(getContext(), "点击: " + news.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFavoriteClick(News news, int position) {
                news.setFavorite(!news.isFavorite());
                adapter.notifyItemChanged(position);
                String msg = news.isFavorite() ? "已收藏" : "已取消收藏";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        loadFinanceNews();
    }

    private void updateStockIndices() {
        tvShanghai.setText("3100.25");
        tvShenzhen.setText("9500.50");
        tvChuangye.setText("1850.75");
    }

    private void loadFinanceNews() {
        List<News> newsList = new ArrayList<>();

        newsList.add(new News(18, "A股三大指数集体大涨",
                "沪指重返3100点，成交额突破万亿...",
                "A股今日强势反弹，沪指涨1.5%重返3100点，两市成交额突破1.2万亿。",
                "https://picsum.photos/id/16/400/200",
                "财经日报", "2024-01-15", "财经"));

        newsList.add(new News(19, "央行宣布降准0.5个百分点",
                "释放长期资金约1万亿...",
                "央行宣布下调金融机构存款准备金率0.5个百分点，释放长期资金约1万亿元。",
                "https://picsum.photos/id/17/400/200",
                "金融时报", "2024-01-14", "财经"));

        newsList.add(new News(20, "房地产市场迎政策利好",
                "首付比例下调，贷款利率优惠...",
                "住建部联合多部门出台房地产新政，首套房首付比例降至15%。",
                "https://picsum.photos/id/18/400/200",
                "地产观察", "2024-01-13", "财经"));

        newsList.add(new News(21, "新能源汽车销量创新高",
                "2023年销量突破900万辆...",
                "2023年新能源汽车销量达950万辆，同比增长30%，市场渗透率达35%。",
                "https://picsum.photos/id/19/400/200",
                "汽车财经", "2024-01-12", "财经"));

        adapter.updateList(newsList);
    }
}