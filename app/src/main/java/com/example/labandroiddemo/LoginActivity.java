package com.example.labandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends MainActivity {
    private ActivityLoginBinding binding;
    private BlackJackRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BlackJackRepository.getRepository(getApplication());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMaker("Sign Up button clicked!"); // Placeholder
            }
        });
    }

    private void verifyUser() {
        String username = binding.userNameLogIn.getText().toString();
        String password = binding.passwordLogIn.getText().toString();
        if (username.isEmpty()) {
            ToastMaker("Username should not be blank!");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                if(password.equals(user.getPassword())) {
                    if (user.isAdmin()) {
                        ToastMaker("Welcome Admin!");
                    }
                    else {
                        Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                        startActivity(intent);
                    }
                    userObserver.removeObservers(this);
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
        });
    }
}
