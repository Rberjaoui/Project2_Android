package com.example.labandroiddemo;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.labandroiddemo.Receiver.Receiver;

public class BlackJackGame extends AppCompatActivity {

    private static final String CHANNEL_ID = "MESSAGE";

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_blackjack); // connect to game screen

        Intent intent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.happy)
                .setContentTitle("JackRush")
                .setContentText("Your current rank is: ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    1000
            );
            return;
        }

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());

    }


    private Deck deck;
    private Card[] playerHand = new Card[12];
    private Card[] dealerHand = new Card[12];

    private int playerCount = 0;
    private int dealerCount = 0;

    public BlackJackGame(Deck incomingDeck) {
        this.deck = incomingDeck;
    }

    public void startRound(){
        playerCount = 0;
        dealerCount = 0;
        deck.shuffle();

        playerHand[playerCount++] = deck.deal();
        playerHand[playerCount++] = deck.deal();

        dealerHand[dealerCount++] = deck.deal();
        dealerHand[dealerCount++] = deck.deal();
    }

    public Card playerHit() {
        playerHand[playerCount++] = deck.deal();
        return null;
    }

    public void dealerTurn() {
        while (getHandValue(dealerHand, dealerCount) < 17) {
            dealerHand[dealerCount++] = deck.deal();
        }
    }

    public Card[] getPlayerHand() {
        Card[] arr = new Card[playerCount];
        for (int i = 0; i < playerCount; i++) {
            arr[i] = playerHand[i];
        }
        return arr;
    }

    public Card[] getDealerHand() {
        Card[] arr = new Card[dealerCount];
        for (int i = 0; i < dealerCount; i++) {
            arr[i] = dealerHand[i];
        }
        return arr;
    }

    public int getPlayerValue() {
        return getHandValue(playerHand, playerCount);
    }

    public int getDealerValue() {
        return getHandValue(dealerHand, dealerCount);
    }

    public String getResult() {
        int p = getPlayerValue();
        int d = getDealerValue();

        if (p > 21) return "You bust!";
        if (d > 21) return "Dealer busts! You win!";
        if (p > d) return "You win!";
        if (d > p) return "Dealer wins!";
        return "Tie!";
    }

    private int getHandValue(Card[] hand, int count) {
        int total = 0;
        int aces = 0;

        for (int i = 0; i < count; i++) {
            int r = hand[i].getRank();
            if (r == 1) {
                aces++;
                total += 11;
            } else if (r > 10) {
                total += 10;
            } else {
                total += r;
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }
}
