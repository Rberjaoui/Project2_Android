package com.example.labandroiddemo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MainActivityTest {

    private Wallet wallet;
    private int betAmount;

    @Before
    public void setup() {
        wallet = new Wallet(500);
        betAmount = 50;
    }

    @Test
    public void testPlayerWin_WalletAndStatsUpdate(){
        int initialBalance = wallet.getBalance();

        wallet.win(betAmount);

        assertEquals(initialBalance + betAmount, wallet.getBalance());
        assertEquals(550, wallet.getBalance());
    }

    @Test
    public void testPlayerLoss_WalletAndStatsUpdate() {
        int initialBalance = wallet.getBalance();

        wallet.lose(betAmount);

        assertEquals(initialBalance - betAmount, wallet.getBalance());
        assertEquals(450, wallet.getBalance());
    }
}