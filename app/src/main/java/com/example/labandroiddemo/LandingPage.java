package com.example.labandroiddemo;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;

import com.example.labandroiddemo.Receiver.Receiver;
import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.database.entities.User;
import com.example.labandroiddemo.databinding.LandingPageBinding;

public class LandingPage extends AppCompatActivity {
    private static final int REQ_POST_NOTIFICATIONS = 100;

    private int loggedInUserId = -1;
    private User user;
    private LandingPageBinding binding;
    private BlackJackRepository repository;
    private static final int LOGGED_OUT = -1;
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.labandroiddemo.SHARED_PREFERENCE_USERID_KEY";

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.labandroiddemo.MAIN_ACTIVITY_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("LandingPage", ">>> onCreate: screen started");
        repository = BlackJackRepository.getRepository(getApplication());

        loginUser(savedInstanceState);

        binding.leaderboardButton.setOnClickListener(v -> {
            Intent intent = LeaderboardActivity.leaderboardIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        Toast.makeText(this, "LandingPage started!", Toast.LENGTH_SHORT).show();
        Log.d("LandingPage", ">>> onCreate reached");

        Button playButton = findViewById(R.id.play);
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, BlackJack.class);
            startActivity(intent);
        });

        createNotificationChannel();
        notificationPermission();

        Button btnNotification = findViewById(R.id.btnTestNotification);
        btnNotification.setOnClickListener(this::sendBroadcast);

    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null &&  savedInstanceState.containsKey(SHARED_PREFERENCE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if(loggedInUserId == LOGGED_OUT){
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            Log.d("LandingPage", ">>> user=" + (user == null ? "null" : user.getUsername())
                    + ", isAdmin=" + (user != null && user.isAdmin()));
            if(this.user == null) {
                invalidateOptionsMenu();
            }
           binding.adminTextview.setText("Welcome " + user.getUsername() + "!");

            if(user.isAdmin()){
//                binding.adminTextview.setText("Welcome Admin!");
//                binding.adminTextview.setVisibility(View.VISIBLE);
                binding.adminButt.setVisibility(View.VISIBLE);
            } else {
                //binding.adminTextview.setVisibility(View.INVISIBLE);
                binding.adminButt.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return true;
        }
        item.setTitle("LOG OUT");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandingPage.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    public void sendBroadcast(View view){
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("data", "Your current rank is: ");
        sendBroadcast(intent);
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreferences();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,loggedInUserId);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreferences(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor  = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedInUserId);
        sharedPrefEditor.apply();

    }

    private void notificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_POST_NOTIFICATIONS
                );
            }
        }
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if(notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

}
