package com.example.labandroiddemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labandroiddemo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override

    private JackRushViewModel  jackRushViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        jackRushViewModel = new ViewModelProvider(this).get(jackRushViewModel.class);



    }

}