package com.example.ai_news_summary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.adapters.NewsAdapter;
import com.example.ai_news_summary.models.News;
import java.util.ArrayList;
import java.util.List;

public class TechnologyFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_technology, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadTechnologyNews();
    }

    private void loadTechnologyNews() {
        List<News> newsList = new ArrayList<>();

        newsList.add(new News(5, "苹果发布M3芯片，性能提升50%",
                "全新3nm工艺，GPU支持光线追踪...",
                "苹果今日发布M3芯片，采用3nm工艺，CPU性能提升30%，GPU性能提升50%，首次支持硬件加速光线追踪。",
                "https://picsum.photos/id/4/400/200",
                "科技日报", "2024-01-15", "科技"));

        newsList.add(new News(6, "特斯拉Cybertruck正式交付",
                "不锈钢车身，百公里加速2.9秒...",
                "特斯拉Cybertruck今日开始交付，起售价60990美元，续航最高547公里。",
                "https://picsum.photos/id/5/400/200",
                "汽车之家", "2024-01-14", "科技"));

        newsList.add(new News(7, "华为Mate 70系列曝光",
                "搭载麒麟9100，卫星通信升级...",
                "华为Mate 70系列预计搭载麒麟9100芯片，支持5.5G网络，卫星通信功能全面升级。",
                "https://picsum.photos/id/6/400/200",
                "手机中国", "2024-01-13", "科技"));

        newsList.add(new News(8, "OpenAI发布GPT-5预览版",
                "多模态能力大幅提升...",
                "OpenAI发布GPT-5预览版，支持文本、图像、音频、视频多模态理解。",
                "https://picsum.photos/id/7/400/200",
                "AI前线", "2024-01-12", "科技"));

        adapter.updateList(newsList);
    }
}