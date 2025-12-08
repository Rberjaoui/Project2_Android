package com.example.labandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labandroiddemo.database.BlackJackDatabase;
import com.example.labandroiddemo.database.UserDAO;
import com.example.labandroiddemo.database.entities.User;

public class SignUp extends AppCompatActivity {
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button signUpButt;
    private BlackJackDatabase db;
    private UserDAO userDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        editTextUsername = findViewById(R.id.nameInput);
        editTextEmail = findViewById(R.id.emailInput);
        editTextPassword = findViewById(R.id.passowrdInput);

        signUpButt = findViewById(R.id.signupButt);

        db = BlackJackDatabase.getDatabase(this);
        userDao = db.userDAO();

        signUpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpInfo();
            }
        });
    }

    private void signUpInfo() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty() || username.isEmpty()){
            Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "Password is too short, must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    User existingUser = userDao.getUserByEmail(email);
                    if(existingUser != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUp.this,
                                        "User with this email already exists", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    User newUser = new User(username, password);
                    newUser.setEmail(email);
                    userDao.insert(newUser);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUp.this, "Yippee!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUp.this, "ERRORRRR: "+ e.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, FirstPage.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isValidEmail(String email){
        return email != null && email.contains("@") && email.contains(".");
    }
}