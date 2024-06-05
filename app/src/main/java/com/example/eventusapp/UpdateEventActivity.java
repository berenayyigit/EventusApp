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

public class UpdateEventActivity extends AppCompatActivity {

    private ExecutorService executorService;
    private Handler uiHandler;
    private EventRepo eventRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // For example, show a Toast message with the result
                String result = (String) msg.obj;
                Toast.makeText(UpdateEventActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Initialize EventRepo
        eventRepo = new EventRepo();

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get references to the UI elements
        EditText editTextEventId = findViewById(R.id.editTextEventId);
        EditText editTextOrgId = findViewById(R.id.editTextOrgId);
        EditText editTextEventName = findViewById(R.id.editTextEventName);
        EditText editTextEventIntro = findViewById(R.id.editTextEventIntro);
        EditText editTextLoc = findViewById(R.id.editTextLoc);
        EditText editTextEventYear = findViewById(R.id.editTextEventYear);
        EditText editTextEventMonth = findViewById(R.id.editTextEventMonth);
        EditText editTextEventDay = findViewById(R.id.editTextEventDay);
        EditText editTextEventHour = findViewById(R.id.editTextEventHour);
        EditText editTextEventMinute = findViewById(R.id.editTextEventMinute);
        Button buttonUpdateEvent = findViewById(R.id.buttonUpdateEvent);

        // Set click listener for the Update button
        buttonUpdateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect data from the input fields
                String eventId = editTextEventId.getText().toString();
                String orgId = editTextOrgId.getText().toString();
                String eventName = editTextEventName.getText().toString();
                String eventIntro = editTextEventIntro.getText().toString();
                String loc = editTextLoc.getText().toString();
                String eventYear = editTextEventYear.getText().toString();
                String eventMonth = editTextEventMonth.getText().toString();
                String eventDay = editTextEventDay.getText().toString();
                String eventHour = editTextEventHour.getText().toString();
                String eventMinute = editTextEventMinute.getText().toString();

                // Call updateEvent method from EventRepo
                eventRepo.updateEvent(executorService, eventId, orgId, eventName, eventIntro, loc, eventYear, eventMonth, eventDay, eventHour, eventMinute, uiHandler);

                // You might want to add an Intent to go back to the previous activity or any other navigation logic
                Intent intent = new Intent(UpdateEventActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
