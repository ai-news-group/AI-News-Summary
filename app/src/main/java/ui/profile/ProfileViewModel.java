package com.example.ai_news_summary.ui.profile;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ai_news_summary.core.database.AppDatabase;
import com.example.ai_news_summary.model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProfileViewModel(Application application) {
        super(application);
        loadUser();
    }

    public LiveData<User> getUser() {
        return user;
    }

    private void loadUser() {
        executorService.execute(() -> {
            // 获取数据库实例（需要你根据项目实际写法调整）
            AppDatabase db = AppDatabase.getInstance(getApplication());
            UserDao userDao = db.userDao();

            // 假设先获取 id=1 的用户（测试用）
            User currentUser = userDao.getUserById(1);
            if (currentUser == null) {
                currentUser = new User();
                currentUser.setId(1);
                currentUser.setUsername("测试用户");
                currentUser.setEmail("test@example.com");
                currentUser.setBio("这是我的个人中心");
                userDao.insert(currentUser);
            }

            final User finalUser = currentUser;
            user.postValue(finalUser);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}