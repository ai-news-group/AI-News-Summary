package com.example.ai_news_summary.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.adapters.BannerAdapter;
import com.example.ai_news_summary.adapters.NewsAdapter;
import com.example.ai_news_summary.models.News;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;

public class HeadlineFragment extends Fragment {

    private static final String TAG = "HeadlineFragment";
    private ViewPager2 bannerViewPager;
    private TabLayout bannerIndicator;
    private TabLayout categoryTabLayout;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private Handler autoScrollHandler = new Handler(Looper.getMainLooper());
    private Runnable autoScrollRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView 被调用");
        return inflater.inflate(R.layout.fragment_headline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated 被调用");

        initViews(view);
        setupBanner();
        setupCategoryTabs();
        setupNewsList();
        startAutoScroll();
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: 初始化视图");
        bannerViewPager = view.findViewById(R.id.bannerViewPager);
        bannerIndicator = view.findViewById(R.id.bannerIndicator);
        categoryTabLayout = view.findViewById(R.id.categoryTabLayout);
        recyclerView = view.findViewById(R.id.recyclerView);

        Log.d(TAG, "bannerViewPager = " + bannerViewPager);
        Log.d(TAG, "recyclerView = " + recyclerView);
    }

    private void setupBanner() {
        Log.d(TAG, "setupBanner: 设置轮播图");

        List<String> bannerImages = new ArrayList<>();
        bannerImages.add("https://picsum.photos/id/0/800/400");
        bannerImages.add("https://picsum.photos/id/1/800/400");
        bannerImages.add("https://picsum.photos/id/2/800/400");
        bannerImages.add("https://picsum.photos/id/3/800/400");

        BannerAdapter bannerAdapter = new BannerAdapter(bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);

        new TabLayoutMediator(bannerIndicator, bannerViewPager,
                (tab, position) -> {}
        ).attach();

        Log.d(TAG, "setupBanner: 完成，图片数量 = " + bannerImages.size());
    }

    private void setupCategoryTabs() {
        Log.d(TAG, "setupCategoryTabs: 设置分类标签");

        categoryTabLayout.addTab(categoryTabLayout.newTab().setText("推荐"));
        categoryTabLayout.addTab(categoryTabLayout.newTab().setText("最新"));
        categoryTabLayout.addTab(categoryTabLayout.newTab().setText("热门"));

        categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "分类标签被选中: position = " + position);
                loadNewsByCategory(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        Log.d(TAG, "setupCategoryTabs: 完成，标签数量 = " + categoryTabLayout.getTabCount());
    }

    private void setupNewsList() {
        Log.d(TAG, "setupNewsList: 设置新闻列表");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter(new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);

        // 加载默认新闻（推荐）
        loadNewsByCategory(0);

        Log.d(TAG, "setupNewsList: 完成");
    }

    private void loadNewsByCategory(int category) {
        Log.d(TAG, "loadNewsByCategory: 加载分类 " + category);

        List<News> newsList = new ArrayList<>();

        if (category == 0) { // 推荐新闻
            Log.d(TAG, "加载推荐新闻");
            newsList.add(new News(1, "重大突破：科学家发现新的癌症治疗方法",
                    "一项突破性研究为癌症治疗带来新希望，临床试验显示显著效果...",
                    "近日，国际科研团队在癌症治疗领域取得重大突破。研究表明，新型免疫疗法能够有效识别并摧毁癌细胞，同时保护健康细胞。临床试验中，80%的患者肿瘤明显缩小。",
                    "https://picsum.photos/id/0/400/200", "张科研", "2024-01-15", "头条"));

            newsList.add(new News(2, "人工智能引领新一轮产业革命",
                    "AI技术正在深刻改变各行各业，智能化转型成为企业共识...",
                    "随着ChatGPT等大模型的普及，人工智能正在引发新一轮产业革命。制造业、金融业、医疗行业纷纷加速智能化转型。",
                    "https://picsum.photos/id/1/400/200", "王科技", "2024-01-14", "头条"));

            newsList.add(new News(3, "新能源汽车销量再创新高",
                    "2023年销量突破900万辆，市场渗透率达35%...",
                    "中国汽车工业协会数据显示，2023年新能源汽车销量达到950万辆，同比增长50%。",
                    "https://picsum.photos/id/2/400/200", "李汽车", "2024-01-13", "头条"));

            newsList.add(new News(4, "全国两会今日开幕",
                    "代表委员齐聚北京，共商国是...",
                    "全国两会今日在北京隆重开幕，来自各地的代表委员带着人民的重托，共商国家发展大计。",
                    "https://picsum.photos/id/3/400/200", "赵政治", "2024-01-12", "头条"));
        } else if (category == 1) { // 最新新闻
            Log.d(TAG, "加载最新新闻");
            newsList.add(new News(5, "科技创新引领高质量发展",
                    "我国科技实力实现历史性跨越...",
                    "近年来，我国在航天、通信、人工智能等领域取得重大突破。",
                    "https://picsum.photos/id/4/400/200", "孙科技", "2024-01-16", "头条"));

            newsList.add(new News(6, "绿色能源产业快速发展",
                    "太阳能、风能装机容量创新高...",
                    "我国绿色能源产业快速发展，太阳能、风能装机容量均创历史新高。",
                    "https://picsum.photos/id/5/400/200", "周能源", "2024-01-15", "头条"));
        } else { // 热门新闻
            Log.d(TAG, "加载热门新闻");
            newsList.add(new News(7, "热搜第一：明星公益引发关注",
                    "多位明星参与公益活动，传递正能量...",
                    "近日，多位明星积极参与公益活动，用实际行动传递爱心。",
                    "https://picsum.photos/id/6/400/200", "周娱乐", "2024-01-15", "头条"));

            newsList.add(new News(8, "体育赛事引发全民关注",
                    "精彩比赛点燃观众热情...",
                    "昨晚进行的体育比赛中，精彩的对决让观众大呼过瘾。",
                    "https://picsum.photos/id/7/400/200", "吴体育", "2024-01-14", "头条"));
        }

        newsAdapter.updateList(newsList);
        Log.d(TAG, "loadNewsByCategory: 完成，加载了 " + newsList.size() + " 条新闻");
    }

    private void startAutoScroll() {
        Log.d(TAG, "startAutoScroll: 开始自动滚动");

        autoScrollRunnable = () -> {
            if (bannerViewPager != null && bannerViewPager.getAdapter() != null) {
                int currentItem = bannerViewPager.getCurrentItem();
                int totalItems = bannerViewPager.getAdapter().getItemCount();
                if (currentItem < totalItems - 1) {
                    bannerViewPager.setCurrentItem(currentItem + 1, true);
                } else {
                    bannerViewPager.setCurrentItem(0, true);
                }
                autoScrollHandler.postDelayed(autoScrollRunnable, 3000);
            }
        };
        autoScrollHandler.postDelayed(autoScrollRunnable, 3000);

        Log.d(TAG, "startAutoScroll: 完成");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: 销毁视图，停止自动滚动");
        if (autoScrollHandler != null && autoScrollRunnable != null) {
            autoScrollHandler.removeCallbacks(autoScrollRunnable);
        }
    }
}