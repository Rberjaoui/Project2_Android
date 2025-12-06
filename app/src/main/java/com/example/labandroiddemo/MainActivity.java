package com.example.labandroiddemo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;

import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.database.entities.User;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    static final String USER_ID_KEY = "com.example.labandroiddemo.USER_ID_KEY";
    private static final String SHARED_PREF_FILE = "com.example.labandroiddemo.PREFERENCES";
    private LinearLayout playerCardContainer;
    private LinearLayout dealerCardContainer;
    private TextView turnIndicator;
    private Button hitButton;
    private Button stayButton;
    private BlackJack game;
    private BlackJackRepository repository;
    private User currentUser;
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = BlackJackRepository.getRepository(getApplication());

        playerCardContainer = findViewById(R.id.player_card_container);
        dealerCardContainer = findViewById(R.id.dealer_card_container);
        turnIndicator = findViewById(R.id.turn_indicator);
        hitButton = findViewById(R.id.hit_button);
        stayButton = findViewById(R.id.stay_button);

        loggedInUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if (loggedInUserId != -1) {
            LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
            userObserver.observe(this, user -> {
                this.currentUser = user;
                userObserver.removeObservers(this);
            });
        }

        game = new BlackJack();
        startGame();

        hitButton.setOnClickListener(v -> {
            Card newCard = game.playerHit();
            addCardToLayout(playerCardContainer, newCard);

            if (game.getPlayerValue() > 21) {
                endRound("You Bust!", -100);
            }
        });

        stayButton.setOnClickListener(v -> {
            game.dealerTurn();
            updateDealerUI();
            checkWinner();
        });
    }

    private int getLoggedInUserId(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getInt(USER_ID_KEY, -1);
    }

    private void startGame(){
        game.startRound();

        playerCardContainer.removeAllViews();
        dealerCardContainer.removeAllViews();
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        turnIndicator.setText("Your Turn");

        for (Card c : game.getPlayerHand()) {
            addCardToLayout(playerCardContainer, c);
        }


        Card[] dealerCards = game.getDealerHand();
        if (dealerCards.length > 0) {
            addCardToLayout(dealerCardContainer, dealerCards[0]);
            addCardToLayout(dealerCardContainer, null);
        }
    }

    private void updateDealerUI() {
        dealerCardContainer.removeAllViews();
        for (Card c : game.getDealerHand()) {
            addCardToLayout(dealerCardContainer, c);
        }
    }

    private void addCardToLayout(LinearLayout layout, Card card){
        ImageView img = new ImageView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(120, 170));
        if (card != null) {
            img.setImageResource(card.getDrawableId());
        } else {
            img.setImageResource(R.drawable.card_back);
        }
        layout.addView(img);
    }

    private void checkWinner() {
        int p = game.getPlayerValue();
        int d = game.getDealerValue();
        String msg;
        int moneyChange;

        if (p > 21) {
            msg = "You bust!";
            moneyChange = -100;
        } else if (d > 21) {
            msg = "Dealer busts! You win!";
            moneyChange = 100;
        } else if (p > d) {
            msg = "You win!";
            moneyChange = 100;
        } else if (d > p) {
            msg = "Dealer wins!";
            moneyChange = -100;
        } else {
            msg = "Tie!";
            moneyChange = 0;
        }
        endRound(msg, moneyChange);
    }

    private void endRound(String message, int moneyChange) {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        turnIndicator.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (currentUser != null) {
            if (moneyChange > 0) {
                currentUser.setWins(currentUser.getWins() + 1);
                currentUser.setBalance(currentUser.getBalance() + moneyChange);
            } else if (moneyChange < 0) {
                currentUser.setLosses(currentUser.getLosses() + 1);
                currentUser.setBalance(currentUser.getBalance() + moneyChange); // Assumes subtraction
            }
            currentUser.setLastPlayed(LocalDateTime.now());

            repository.insertUser(currentUser);
        }
    }
}
