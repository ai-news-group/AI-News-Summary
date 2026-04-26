package com.example.ai_news_summary.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ai_news_summary.model.FavoriteItem;
import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<List<FavoriteItem>> items = new MutableLiveData<>();
    private MutableLiveData<Boolean> isBatchMode = new MutableLiveData<>(false);
    private MutableLiveData<Integer> selectedCount = new MutableLiveData<>(0);

    public FavoriteViewModel() {
        loadMockData();
    }

    public LiveData<List<FavoriteItem>> getItems() {
        return items;
    }

    public LiveData<Boolean> getIsBatchMode() {
        return isBatchMode;
    }

    public LiveData<Integer> getSelectedCount() {
        return selectedCount;
    }

    private void loadMockData() {
        List<FavoriteItem> mockList = new ArrayList<>();
        mockList.add(new FavoriteItem("1", "GPT-5 预计明年发布，参数量达百万亿",
                "OpenAI CEO Sam Altman 透露下一代模型...", "机器之心",
                System.currentTimeMillis() - 86400000, false));
        mockList.add(new FavoriteItem("2", "Google Gemini 全面接入 Workspace",
                "Google 宣布 Gemini AI 整合至 Gmail、Docs...", "36氪",
                System.currentTimeMillis() - 172800000, true));
        mockList.add(new FavoriteItem("3", "AI 安全峰会签署国际声明",
                "多个国家达成AI安全合作框架...", "澎湃新闻",
                System.currentTimeMillis(), false));
        items.setValue(mockList);
    }

    public void toggleBatchMode() {
        isBatchMode.setValue(!isBatchMode.getValue());
        if (!isBatchMode.getValue()) {
            selectedCount.setValue(0);
        }
    }

    public void setSelectedCount(int count) {
        selectedCount.setValue(count);
    }

    public void deleteSelected(List<String> ids) {
        List<FavoriteItem> currentItems = items.getValue();
        if (currentItems != null) {
            List<FavoriteItem> newItems = new ArrayList<>();
            for (FavoriteItem item : currentItems) {
                if (!ids.contains(item.getId())) {
                    newItems.add(item);
                }
            }
            items.setValue(newItems);
        }
        toggleBatchMode();
    }

    public void markAsRead(String id) {
        List<FavoriteItem> currentItems = items.getValue();
        if (currentItems != null) {
            List<FavoriteItem> newItems = new ArrayList<>();
            for (FavoriteItem item : currentItems) {
                if (item.getId().equals(id)) {
                    item.setRead(true);
                }
                newItems.add(item);
            }
            items.setValue(newItems);
        }
    }
}