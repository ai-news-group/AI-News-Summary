package com.example.ai_news_summary.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ai_news_summary.model.FolderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FolderViewModel extends ViewModel {

    private MutableLiveData<List<FolderItem>> folders = new MutableLiveData<>();

    public FolderViewModel() {
        loadMockData();
    }

    public LiveData<List<FolderItem>> getFolders() {
        return folders;
    }

    private void loadMockData() {
        List<FolderItem> mockList = new ArrayList<>();
        mockList.add(new FolderItem("1", "全部收藏", 12, System.currentTimeMillis() - 86400000 * 30));
        mockList.add(new FolderItem("2", "LLM大模型", 5, System.currentTimeMillis() - 86400000 * 20));
        mockList.add(new FolderItem("3", "AI产品", 4, System.currentTimeMillis() - 86400000 * 15));
        mockList.add(new FolderItem("4", "政策法规", 3, System.currentTimeMillis() - 86400000 * 10));
        folders.setValue(mockList);
    }

    public void addFolder(String name) {
        List<FolderItem> currentFolders = folders.getValue();
        if (currentFolders != null) {
            String newId = UUID.randomUUID().toString();
            FolderItem newFolder = new FolderItem(newId, name, 0);
            currentFolders.add(newFolder);
            folders.setValue(currentFolders);
        }
    }

    public void updateFolder(FolderItem folder) {
        List<FolderItem> currentFolders = folders.getValue();
        if (currentFolders != null) {
            for (int i = 0; i < currentFolders.size(); i++) {
                if (currentFolders.get(i).getId().equals(folder.getId())) {
                    currentFolders.set(i, folder);
                    break;
                }
            }
            folders.setValue(currentFolders);
        }
    }

    public void deleteFolder(FolderItem folder) {
        List<FolderItem> currentFolders = folders.getValue();
        if (currentFolders != null) {
            currentFolders.remove(folder);
            folders.setValue(currentFolders);
        }
    }
}
