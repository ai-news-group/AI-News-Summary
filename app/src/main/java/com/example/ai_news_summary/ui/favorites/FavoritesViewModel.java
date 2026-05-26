package com.example.ai_news_summary.ui.favorites;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ai_news_summary.core.model.Favorite;
import com.example.ai_news_summary.core.model.News;
import com.example.ai_news_summary.data.dao.FavoriteDao;
import com.example.ai_news_summary.data.dao.NewsDao;
import com.example.ai_news_summary.core.database.AppDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesViewModel extends AndroidViewModel {

    private final MutableLiveData<List<News>> favoriteNews = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String currentUserId = "1";

    public FavoritesViewModel(Application application) {
        super(application);
        loadFavorites();
    }

    public LiveData<List<News>> getFavoriteNews() {
        return favoriteNews;
    }

    private void loadFavorites() {
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            FavoriteDao favoriteDao = db.favoriteDao();
            NewsDao newsDao = db.newsDao();

            List<Favorite> favorites = favoriteDao.getFavoritesByUserId(currentUserId);
            List<News> newsList = new ArrayList<>();
            for (Favorite favorite : favorites) {
                int newsId = Integer.parseInt(favorite.getNewsId());
                News news = newsDao.getNewsById(newsId);
                if (news != null) {
                    newsList.add(news);
                }
            }

            favoriteNews.postValue(newsList);
        });
    }

    public void removeFavorite(String newsId) {
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            FavoriteDao favoriteDao = db.favoriteDao();
            favoriteDao.deleteByUserIdAndNewsId(currentUserId, newsId);
            loadFavorites();
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}