package com.example.labandroiddemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WalletActivity extends AppCompatActivity {

    private static final String SHARED_PREF_FILE = "com.example.labandroiddemo_preferences";
    private static final String WALLET_KEY = "com.example.labandroiddemo.WALLET_KEY";

    private EditText walletInput;
    private Button saveButton;
    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        walletInput = findViewById(R.id.wallet_input);
        saveButton = findViewById(R.id.save_wallet_button);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        int currentWallet = prefs.getInt(WALLET_KEY, 1000);
        wallet = new Wallet(currentWallet);
        walletInput.setText(String.valueOf(wallet.getBalance()));

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
            editor.putInt(WALLET_KEY, wallet.getBalance());
            editor.apply();
            Toast.makeText(this, "Wallet updated!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}