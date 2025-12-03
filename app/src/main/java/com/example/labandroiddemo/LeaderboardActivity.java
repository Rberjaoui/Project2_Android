package com.example.labandroiddemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.databinding.ActivityLeaderboardBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private ActivityLeaderboardBinding binding;
    private BlackJackRepository repository;
    private LeaderboardAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BlackJackRepository.getRepository(getApplication());
        setupRecyclerView();

        repository.getLeaderboardUsers().observe(this, users -> {
            if (users != null) {
                adapter.setUsers(users);
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new LeaderboardAdaptor();
        binding.leaderboardRecyclerView.setAdapter(adapter);
        binding.leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static Intent leaderboardIntentFactory(Context context) {
        return new Intent(context, LeaderboardActivity.class);
    }
}
