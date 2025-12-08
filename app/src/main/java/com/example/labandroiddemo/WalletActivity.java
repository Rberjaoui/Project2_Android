package com.example.labandroiddemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.database.entities.User;

public class WalletActivity extends AppCompatActivity {

    private static final String TAG = "WalletActivity";
    private static final String SHARED_PREF_FILE = "com.example.labandroiddemo_preferences";
    private static final String WALLET_KEY = "com.example.labandroiddemo.WALLET_KEY";
    private static final String USER_ID_KEY = "com.example.labandroiddemo.SHARED_PREFERENCE_USERID_KEY";

    private EditText walletInput;
    private Button saveButton;
    private Wallet wallet;
    private BlackJackRepository repository;
    private User currentUser;
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        walletInput = findViewById(R.id.wallet_input);
        saveButton = findViewById(R.id.save_wallet_button);

        repository = BlackJackRepository.getRepository(getApplication());

        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        String userWalletKey = WALLET_KEY + loggedInUserId;
        int currentWallet = prefs.getInt(userWalletKey, Wallet.DEFAULT_BALANCE);
        loggedInUserId = prefs.getInt(USER_ID_KEY, -1);

        wallet = new Wallet(currentWallet);
        walletInput.setText(String.valueOf(wallet.getBalance()));

        loadCurrentUser();

        saveButton.setOnClickListener(v -> {
            String input = walletInput.getText().toString().trim();
            if(input.isEmpty()){
                Toast.makeText(this, "Enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if(input.contains(".")){
                Toast.makeText(this, "Whole number only", Toast.LENGTH_SHORT).show();
                return;
            }
            int newAmount;
            try {
                newAmount = Integer.parseInt(input);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if(newAmount < 0){
                Toast.makeText(this, "Amount must be positive", Toast.LENGTH_SHORT).show();
                return;
            }

            wallet.reset(newAmount);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(userWalletKey, wallet.getBalance());
            editor.apply();

            if(currentUser != null) {
                currentUser.setBalance(wallet.getBalance());
                repository.updateUser(currentUser);
                Log.d(TAG, "Updated user balance to: " + wallet.getBalance());
            }

            Toast.makeText(this, "Wallet updated!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadCurrentUser() {
        if (loggedInUserId != -1) {
            LiveData<User> userLiveData = repository.getUserByUserId(loggedInUserId);
            userLiveData.observe(this, user -> {
                if (user != null) {
                    currentUser = user;
                    wallet.reset(user.getBalance());
                    walletInput.setText(String.valueOf(wallet.getBalance()));
                    Log.d(TAG, "User loaded: " + user.getUsername() + " | Balance: " + user.getBalance());
                }
            });
        }
    }
}