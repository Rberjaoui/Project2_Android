package com.example.labandroiddemo.database;

import android.app.Application;

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
}
