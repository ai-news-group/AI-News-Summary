package com.example.ai_news_summary.ui.editprofile;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ai_news_summary.core.model.User;
import com.example.ai_news_summary.data.dao.UserDao;
import com.example.ai_news_summary.core.database.AppDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String currentUserId = "1";

    public EditProfileViewModel(Application application) {
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
            User currentUser = userDao.getUserById(currentUserId);
            user.postValue(currentUser);
        });
    }

    public void updateUserProfile(String nickname, String phone, String email) {
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            UserDao userDao = db.userDao();
            User currentUser = userDao.getUserById(currentUserId);
            if (currentUser != null) {
                currentUser.setNickname(nickname);
                currentUser.setPhone(phone);
                currentUser.setEmail(email);
                userDao.update(currentUser);
                user.postValue(currentUser);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}