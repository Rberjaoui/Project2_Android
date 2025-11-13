package com.example.labandroiddemo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.labandroiddemo.database.entities.BlackJack;

import java.util.List;

@Dao
public interface BlackJackDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BlackJack blackJack);

    @Query("SELECT * FROM " + BlackJackDatabase.blackJackTable + " ORDER BY date DESC")
    List<BlackJack> getAllRecords();

    @Query("SELECT * FROM " + BlackJackDatabase.blackJackTable + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<BlackJack> getAllRecordsByUserId(int loggedInUserId);

    @Query("SELECT * FROM " + BlackJackDatabase.blackJackTable + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    LiveData<List<BlackJack>> getRecordSetUserIdLiveData(int loggedInUserId);
}
