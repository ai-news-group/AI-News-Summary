package com.example.ai_news_summary.ui.history;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ai_news_summary.core.model.ReadingHistory;
import com.example.ai_news_summary.core.model.News;
import com.example.ai_news_summary.data.dao.ReadingHistoryDao;
import com.example.ai_news_summary.data.dao.NewsDao;
import com.example.ai_news_summary.core.database.AppDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryViewModel extends AndroidViewModel {

    private final MutableLiveData<List<News>> historyNews = new MutableLiveData<>();
    private final MutableLiveData<List<Long>> readTimes = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String currentUserId = "1";

    public HistoryViewModel(Application application) {
        super(application);
        loadHistory();
    }

    public LiveData<List<News>> getHistoryNews() {
        return historyNews;
    }

    private void loadHistory() {
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            ReadingHistoryDao historyDao = db.readingHistoryDao();
            NewsDao newsDao = db.newsDao();

            List<ReadingHistory> histories = historyDao.getHistoryByUserId(currentUserId);
            List<News> newsList = new ArrayList<>();
            List<Long> times = new ArrayList<>();

            for (ReadingHistory history : histories) {
                int newsId = Integer.parseInt(history.getNewsId());
                News news = newsDao.getNewsById(newsId);
                if (news != null) {
                    newsList.add(news);
                    times.add(history.getReadTime());
                }
            }

            historyNews.postValue(newsList);
            readTimes.postValue(times);
        });
    }

    public void clearHistory() {
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            ReadingHistoryDao historyDao = db.readingHistoryDao();
            historyDao.deleteAllByUserId(currentUserId);
            loadHistory();
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}