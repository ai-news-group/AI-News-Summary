package com.example.ai_news_summary.ui.profile;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ai_news_summary.core.model.User;
import com.example.ai_news_summary.data.dao.UserDao;
import com.example.ai_news_summary.core.database.AppDatabase;
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
            AppDatabase db = AppDatabase.getInstance(getApplication());
            UserDao userDao = db.userDao();

            // 尝试获取第一个用户
            User currentUser = userDao.getFirstUser();

            // 如果没有用户，创建一个测试用户
            if (currentUser == null) {
                currentUser = new User();
                currentUser.setUserId(System.currentTimeMillis() + ""); // 用时间戳作为临时ID
                currentUser.setNickname("测试用户");
                currentUser.setEmail("test@example.com");
                currentUser.setPhone("13800138000");
                currentUser.setRegisterTime(System.currentTimeMillis());
                currentUser.setLastLoginTime(System.currentTimeMillis());
                userDao.insert(currentUser);

                // 重新获取刚插入的用户（确保ID正确）
                currentUser = userDao.getFirstUser();
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