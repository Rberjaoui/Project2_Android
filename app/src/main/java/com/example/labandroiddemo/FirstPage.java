package com.example.labandroiddemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.labandroiddemo.USER_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        Button loginButton = findViewById(R.id.button2);
        Button signupButton = findViewById(R.id.button);

        loginButton.setOnClickListener(v -> {
            Intent i = new Intent(FirstPage.this, LoginActivity.class);
            startActivity(i);
        });

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(FirstPage.this, SignUp.class);
            startActivity(intent);
        });
    }

    public static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}
