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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeleteEventActivity extends AppCompatActivity {

    private ExecutorService executorService;
    private Handler uiHandler;
    private EventRepo eventRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // For example, show a Toast message with the result
                String result = (String) msg.obj;
                Toast.makeText(DeleteEventActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Initialize EventRepo
        eventRepo = new EventRepo();

        // Get reference to the UI element
        EditText editTextEventId = findViewById(R.id.editTextEventId);
        Button buttonDeleteEvent = findViewById(R.id.buttonDeleteEvent);

        // Set click listener for the Delete button
        buttonDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the event ID from the input field
                String eventId = editTextEventId.getText().toString();

                // Call deleteEvent method from EventRepo
                eventRepo.deleteEvent(executorService, eventId, uiHandler);

                Intent intent = new Intent(DeleteEventActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
