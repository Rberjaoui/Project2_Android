package com.example.labandroiddemo.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BlackJackRepository {
    private final BlackJackDAO blackJackDAO;
    private final UserDAO userDAO;
    private static BlackJackRepository repository;

    private BlackJackRepository(Application application) {
        BlackJackDatabase db = BlackJackDatabase.getDatabase(application);
        this.blackJackDAO = db.blackjackDAO();
        this.userDAO = db.userDAO();
    }

    public static BlackJackRepository getRepository(Application application) {
        if (repository == null) {
            synchronized (BlackJackRepository.class) {
                if (repository == null) {
                    repository = new BlackJackRepository(application);
                }
            }
        }
        return repository;
    }

    public void insertGymLog(BlackJack blackJack) {
        BlackJackDatabase.databaseWriteExecutor.execute(() -> {
            blackJackDAO.insert(blackJack);
        });
    }

    public void insertUser(User... user) {
        BlackJackDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<BlackJack>> getAllLogsByUserIdLiveData(int loggedInUserId) {
        return blackJackDAO.getRecordSetUserIdLiveData(loggedInUserId);
    }
}
