package com.example.labandroiddemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREF_FILE = "com.example.labandroiddemo_preferences";
    static final String USER_ID_KEY = "com.example.labandroiddemo.SHARED_PREFERENCE_USERID_KEY";

    private int loggedInUserId = -1;

    private Deck deck;
    private BlackJack game;

    private LinearLayout playerCardContainer, dealerCardContainer;
    private TextView turnIndicator;
    private Button hitButton, stayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerCardContainer = findViewById(R.id.player_card_container);
        dealerCardContainer = findViewById(R.id.dealer_card_container);
        turnIndicator = findViewById(R.id.turn_indicator);
        hitButton = findViewById(R.id.hit_button);
        stayButton = findViewById(R.id.stay_button);

        loggedInUserId = getLoggedInUserId();

        deck = new Deck();
        game = new BlackJack(deck);
        game.startRound();

        showInitialCards();

        hitButton.setOnClickListener(v -> {
            game.playerHit();
            Card[] hand = game.getPlayerHand();
            addCardToLayout(playerCardContainer, hand[hand.length - 1]);

            if (game.getPlayerValue() > 21) {
                showDealerCards();
                turnIndicator.setText("You Bust!");
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
            }
        });

        stayButton.setOnClickListener(v -> {
            game.dealerTurn();
            showDealerCards();
            turnIndicator.setText(game.getResult());
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        });
    }

    private int getLoggedInUserId(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getInt(USER_ID_KEY, -1);
    }

    private void showInitialCards(){
        playerCardContainer.removeAllViews();
        dealerCardContainer.removeAllViews();

        for (Card c : game.getPlayerHand())
            addCardToLayout(playerCardContainer, c);

        addCardToLayout(dealerCardContainer, game.getDealerHand()[0]);
        addCardToLayout(dealerCardContainer, null);
    }

    private void showDealerCards(){
        dealerCardContainer.removeAllViews();
        for (Card c : game.getDealerHand())
            addCardToLayout(dealerCardContainer, c);
    }

    private void addCardToLayout(LinearLayout layout, Card card){
        ImageView img = new ImageView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(120, 170));
        img.setImageResource(card != null ? card.getDrawableId() : R.drawable.card_back);
        layout.addView(img);
    }
}
