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

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchEventDateActivity extends AppCompatActivity {

    private ExecutorService executorService;
    private Handler uiHandler;
    private EventRepo eventRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event_date);

        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // Retrieve the search results from the message object
                List<Event> searchResults = (List<Event>) msg.obj;

                // Check if searchResults is not null and not empty
                if (searchResults != null && !searchResults.isEmpty()) {
                    // Create an intent to navigate to the activity to display search results
                    Intent intent = new Intent(SearchEventDateActivity.this, DisplaySearchResultsActivity.class);

                    // Pass the search results to the next activity using Intent extras
                    intent.putExtra("searchResults", (Serializable) searchResults);

                    // Start the activity
                    startActivity(intent);
                } else {
                    // If no search results were found, display a message
                    Toast.makeText(SearchEventDateActivity.this, "No events found for the selected date.", Toast.LENGTH_SHORT).show();
                }

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
        EditText editTextEventYear = findViewById(R.id.editTextEventYear);
        EditText editTextEventMonth = findViewById(R.id.editTextEventMonth);
        EditText editTextEventDay = findViewById(R.id.editTextEventDay);
        EditText editTextEventHour = findViewById(R.id.editTextEventHour);
        EditText editTextEventMinute = findViewById(R.id.editTextEventMinute);
        EditText editTextEventTime = findViewById(R.id.editTextEventTime);
        Button buttonSearchEvent = findViewById(R.id.buttonSearchEvent);

        // Set click listener for the Search button
        buttonSearchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect data from the input fields
                String eventYear = editTextEventYear.getText().toString();
                String eventMonth = editTextEventMonth.getText().toString();
                String eventDay = editTextEventDay.getText().toString();
                String eventHour = editTextEventHour.getText().toString();
                String eventMinute = editTextEventMinute.getText().toString();
                String eventTime = editTextEventTime.getText().toString();

                // Call searchEventsByDate method from EventRepo
                eventRepo.searchEventsByDate(executorService, eventYear, eventMonth, eventDay, eventHour, eventMinute, eventTime, uiHandler);
            }
        });
    }
}
