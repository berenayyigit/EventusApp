package com.example.eventusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DisplaySearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);

        // Get the search results from the Intent extras
        List<Event> searchResults = (List<Event>) getIntent().getSerializableExtra("searchResults");

        // Display the search results
        displayResults(searchResults);

        // Set up the back button click listener
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                // Create an intent to navigate back to the MainActivity
                Intent intent = new Intent(DisplaySearchResultsActivity.this, MainActivity.class);
                // Set the intent flags to clear the back stack and start the MainActivity as a new task
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Start the MainActivity
                startActivity(intent);
                // Finish the current activity
                finish();
            }
        });
    }

    private void displayResults(List<Event> searchResults) {
        TextView textViewResults = findViewById(R.id.textViewResults);

        // Check if searchResults is not null and not empty
        if (searchResults != null && !searchResults.isEmpty()) {
            StringBuilder resultText = new StringBuilder();

            // Append each search result to the resultText
            for (Event event : searchResults) {
                resultText.append("Event Name: ").append(event.getName()).append("\n");
                resultText.append("Organization: ").append(event.getOrg()).append("\n");
                resultText.append("Location: ").append(event.getLoc()).append("\n");
                resultText.append("Date: ").append(event.getEventDate()).append("\n");
                resultText.append("Time: ").append(event.getEventTime()).append("\n\n");
            }

            // Set the resultText to the textViewResults
            textViewResults.setText(resultText.toString());
        } else {
            // If no search results were found, display a message
            textViewResults.setText("No events found for the selected date.");
        }
    }
}
