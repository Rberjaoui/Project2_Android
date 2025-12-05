package com.example.labandroiddemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.database.entities.User;
import com.example.labandroiddemo.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private BlackJackRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        repository = BlackJackRepository.getRepository(getApplication());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username = binding.userNameLogIn.getText().toString().trim();
        String password = binding.passwordLogIn.getText().toString().trim();
        if (username.isEmpty()) {
            ToastMaker("Username should not be blank!");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                if(password.equals(user.getPassword())) {
                    Intent intent = new Intent(this, LandingPage.class);
                    intent.putExtra("com.example.labandroiddemo.MAIN_ACTIVITY_USER_ID", user.getId());

                    if (user.isAdmin()) {
                        ToastMaker("Welcome Admin!");
                        startActivity(intent);
                    }
                    else {
                        startActivity(intent);
                    }
                    finish();
                }
                else {
                    ToastMaker("Invalid password");
                    binding.passwordLogIn.setSelection(0);
                }
            }
            else {
                ToastMaker(String.format("No User %s found", username));
                binding.userNameLogIn.setSelection(0);
            }
            userObserver.removeObservers(this);
        });

    }

    private void ToastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
