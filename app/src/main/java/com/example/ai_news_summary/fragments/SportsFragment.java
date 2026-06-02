package com.example.ai_news_summary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SportsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        loadSportsNews();
    }

    private void loadSportsNews() {
        List<News> newsList = new ArrayList<>();

        newsList.add(new News(9, "NBA：詹姆斯突破40000分",
                "历史第一人，创造新的里程碑...",
                "詹姆斯在对阵掘金的比赛中突破40000分大关，成为NBA历史得分王。",
                "https://picsum.photos/id/8/400/200",
                "体育新闻", "2024-01-15", "体育"));

        newsList.add(new News(10, "欧冠：皇马3-1逆转曼城",
                "贝林厄姆梅开二度...",
                "欧冠1/4决赛，皇马主场3-1逆转曼城，贝林厄姆独中两元。",
                "https://picsum.photos/id/9/400/200",
                "足球报", "2024-01-14", "体育"));

        newsList.add(new News(11, "澳网：郑钦文晋级四强",
                "中国金花创造历史...",
                "郑钦文2-1逆转对手，成为首位打进澳网四强的中国女单选手。",
                "https://picsum.photos/id/10/400/200",
                "网球天地", "2024-01-13", "体育"));

        newsList.add(new News(12, "CBA全明星首发公布",
                "易建联当选票王...",
                "CBA全明星首发阵容公布，易建联以高票当选票王。",
                "https://picsum.photos/id/11/400/200",
                "篮球先锋", "2024-01-12", "体育"));

        adapter.updateList(newsList);
    }
}