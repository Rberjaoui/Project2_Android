package com.example.labandroiddemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREF_FILE = "com.example.labandroiddemo_preferences";
    static final String USER_ID_KEY = "com.example.labandroiddemo.SHARED_PREFERENCE_USERID_KEY";

    private int loggedInUserId = -1;
    private int winStreak = 0;

    private Deck deck;
    private LinearLayout playerCardContainer, dealerCardContainer;
    private TextView turnIndicator, winStreakText;
    private Button hitButton, stayButton, retryButton, quitButton;

    private BlackJack game;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerCardContainer = findViewById(R.id.player_card_container);
        dealerCardContainer = findViewById(R.id.dealer_card_container);
        turnIndicator = findViewById(R.id.turn_indicator);
        hitButton = findViewById(R.id.hit_button);
        stayButton = findViewById(R.id.stay_button);
        retryButton = findViewById(R.id.retry_button);
        quitButton = findViewById(R.id.quit_button);
        winStreakText = findViewById(R.id.win_streak_text);

        loggedInUserId = getLoggedInUserId();

        deck = new Deck();
        deck.shuffle();
        game = new BlackJack(deck);

        startGame();

        hitButton.setOnClickListener(v -> {
            game.playerHit();
            addCardToLayout(playerCardContainer, game.getPlayerHand()[game.getPlayerHand().length - 1]);
            if(game.getPlayerValue() > 21){
                endRound();
            }
        });

        stayButton.setOnClickListener(v -> endRound());

        retryButton.setOnClickListener(v -> {
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
            retryButton.setVisibility(View.GONE);
            quitButton.setVisibility(View.GONE);
            deck = new Deck();
            deck.shuffle();
            game = new BlackJack(deck);
            startGame();
        });

        quitButton.setOnClickListener(v -> finish());
    }

    private int getLoggedInUserId(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getInt(USER_ID_KEY, -1);
    }

    private void startGame(){
        playerCardContainer.removeAllViews();
        dealerCardContainer.removeAllViews();

        game.startRound();

        for(Card c : game.getPlayerHand()){
            addCardToLayout(playerCardContainer, c);
        }

        for(Card c : game.getDealerHand()){
            addCardToLayout(dealerCardContainer, c);
        }

        turnIndicator.setText("Player's Turn");
        winStreakText.setText("\uD83D\uDD25 : " + winStreak);
        retryButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.GONE);
    }

    private void addCardToLayout(LinearLayout layout, Card card){
        ImageView img = new ImageView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(120, 170));
        if(card != null){
            img.setImageResource(card.getDrawableId());
        } else {
            img.setImageResource(R.drawable.card_back);
        }
        layout.addView(img);
    }

    private void dealerPlay(){
        game.dealerTurn();
        dealerCardContainer.removeAllViews();
        for(Card c : game.getDealerHand()){
            addCardToLayout(dealerCardContainer, c);
        }
    }

    private void endRound(){
        dealerPlay();
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        String result = game.getResult();
        turnIndicator.setText(result);

        if(result.contains("You win")){
            winStreak++;
        }else if(result.contains("Dealer wins!")){
            winStreak = 0;
        }

        winStreakText.setText("\uD83D\uDD25 : " + winStreak);
        retryButton.setVisibility(View.VISIBLE);
        quitButton.setVisibility(View.VISIBLE);
    }
}
