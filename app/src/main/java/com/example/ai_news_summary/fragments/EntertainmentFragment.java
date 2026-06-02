package com.example.ai_news_summary.fragments;

import android.os.Bundle;
import android.util.Log;
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

public class EntertainmentFragment extends Fragment {

    private static final String TAG = "EntertainmentFragment";
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView 被调用");
        return inflater.inflate(R.layout.fragment_entertainment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated 被调用");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadEntertainmentNews();
    }

    private void loadEntertainmentNews() {
        Log.d(TAG, "loadEntertainmentNews 开始加载");

        List<News> newsList = new ArrayList<>();

        newsList.add(new News(13, "《热辣滚烫》票房破30亿",
                "贾玲导演作品再创佳绩，成为春节档票房冠军...",
                "春节档电影《热辣滚烫》票房突破30亿，贾玲成为票房最高的女导演。影片讲述了一个普通人追寻梦想的感人故事。",
                "https://picsum.photos/id/12/400/200",
                "娱乐周刊", "2024-01-15", "娱乐"));

        newsList.add(new News(14, "周杰伦演唱会官宣",
                "5月上海首站，连开四场，门票即将开售...",
                "周杰伦嘉年华世界巡回演唱会官宣，5月16-19日上海连开四场，预计将吸引数万粉丝到场。",
                "https://picsum.photos/id/13/400/200",
                "音乐之声", "2024-01-14", "娱乐"));

        newsList.add(new News(15, "《歌手2024》阵容公布",
                "那英、林俊杰、海外歌手强势加盟...",
                "湖南卫视《歌手2024》公布首发阵容，那英、林俊杰以及多位海外实力唱将加盟，节目将于下周五首播。",
                "https://picsum.photos/id/14/400/200",
                "综艺报", "2024-01-13", "娱乐"));

        newsList.add(new News(16, "奥斯卡获奖名单揭晓",
                "《奥本海默》获最佳影片，诺兰首夺小金人...",
                "第96届奥斯卡颁奖礼举行，《奥本海默》获得最佳影片等7项大奖，诺兰终于获得首个奥斯卡最佳导演。",
                "https://picsum.photos/id/15/400/200",
                "影视圈", "2024-01-12", "娱乐"));

        newsList.add(new News(17, "王一博新剧开机",
                "搭档实力派演员，引发期待...",
                "王一博新剧《追风者》今日开机，搭档实力派演员，讲述民国谍战故事，引发粉丝热烈期待。",
                "https://picsum.photos/id/20/400/200",
                "剧集资讯", "2024-01-11", "娱乐"));

        adapter.updateList(newsList);
        Log.d(TAG, "loadEntertainmentNews 完成，加载了 " + newsList.size() + " 条新闻");
    }
}