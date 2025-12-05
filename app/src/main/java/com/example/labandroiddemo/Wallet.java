package com.example.labandroiddemo;

public class Wallet {

    private int balance;

    public Wallet() {
        this.balance = 0;
    }

    public Wallet(int initBalance) {
        this.balance = initBalance;
    }

    public int getBalance() {
        return balance;
    }

    public boolean betAmount(int amount){
        return amount > 0 && amount <= balance;
    }

    public void win(int amount) {
        if(amount > 0){
            balance += amount;
        }
        // toast ??
    }

    public void lose(int amount){
        if(amount > 0 && balance >= amount){
            balance -= amount;
        }
    }

    public void reset(int newBalance){
        balance = newBalance;
    }
}
