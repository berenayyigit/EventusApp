package com.example.eventusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextUsername = findViewById(R.id.editTextRegisterUsername);
        EditText editTextPassword = findViewById(R.id.editTextRegisterPassword);
        EditText editTextEmail = findViewById(R.id.editTextRegisterName);
        EditText editTextPhone = findViewById(R.id.editTextRegisterLastName);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                // Register the user (store the username, password, email, and phone, usually in a database or shared preferences)
                // This is just a placeholder. Implement actual registration logic here.
                // For demonstration purposes, assuming registration is always successful.

                // After successful registration, navigate to MainActivity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish RegisterActivity so user can't go back to it
            }
        });
    }
}
