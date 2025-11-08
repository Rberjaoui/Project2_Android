package com.example.labandroiddemo;

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


}
