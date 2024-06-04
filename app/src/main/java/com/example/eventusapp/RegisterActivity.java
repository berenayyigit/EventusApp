package com.example.eventusapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {



    private ExecutorService executorService;
    private Handler uiHandler;
    private UserRepo userRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // For example, show a Toast message with the result
                String result = (String) msg.obj;
                Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Initialize EventRepo
        userRepo = new UserRepo();

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        EditText editTextUsername = findViewById(R.id.editTextRegisterUsername);
        EditText editTextPassword = findViewById(R.id.editTextRegisterPassword);
        EditText editTextName = findViewById(R.id.editTextRegisterName);
        EditText editTextLastName = findViewById(R.id.editTextRegisterLastName);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String name = editTextName.getText().toString();
                String lastName = editTextLastName.getText().toString();

                // Register the user (store the username, password, email, and phone, usually in a database or shared preferences)
                // This is just a placeholder. Implement actual registration logic here.
                // For demonstration purposes, assuming registration is always successful.
                userRepo.saveUser(executorService, username, password, name, lastName, uiHandler);

                // After successful registration, navigate to MainActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish RegisterActivity so user can't go back to it
            }
        });
    }
}
