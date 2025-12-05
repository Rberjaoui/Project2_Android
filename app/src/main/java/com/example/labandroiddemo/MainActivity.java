package com.example.labandroiddemo;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.labandroiddemo.USER_ID_KEY";

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
        deck.shuffle();

        startGame();

        hitButton.setOnClickListener(v -> {
            if (deck.hasNext()) {
                Card newCard = deck.deal();
                addCardToLayout(playerCardContainer, newCard);
            }
        });

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });

    }

    private int getLoggedInUserId(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getInt(USER_ID_KEY, -1);
    }

    private void startGame(){
        playerCardContainer.removeAllViews();
        dealerCardContainer.removeAllViews();

        for (int i = 0; i < 2; i++) {
            addCardToLayout(playerCardContainer, deck.deal());
        }

        addCardToLayout(dealerCardContainer, deck.deal());
        addCardToLayout(dealerCardContainer, null);
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

    private void dealerPlay() {
        dealerCardContainer.removeAllViews();
        for (int i = 0; i < 2; i++) {
            addCardToLayout(dealerCardContainer, deck.deal());
        }
    }

}
