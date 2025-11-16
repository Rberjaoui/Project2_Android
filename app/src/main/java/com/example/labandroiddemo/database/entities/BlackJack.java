package com.example.labandroiddemo.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.labandroiddemo.database.BlackJackDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = BlackJackDatabase.blackJackTable)
public class BlackJack {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private LocalDateTime date;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BlackJack blackJack = (BlackJack) o;
        return id == blackJack.id && userId == blackJack.userId && Objects.equals(date, blackJack.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, userId);
    }

    @Override
    public String toString() {
        return "BlackJack{" +
                "id=" + id +
                ", date=" + date +
                ", userID=" + userId +
                '}';
    }
}
