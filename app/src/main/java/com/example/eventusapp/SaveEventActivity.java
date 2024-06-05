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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaveEventActivity extends AppCompatActivity {

    private ExecutorService executorService;
    private Handler uiHandler;
    private EventRepo eventRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_event);


        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // For example, show a Toast message with the result
                String result = (String) msg.obj;
                Toast.makeText(SaveEventActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        // Initialize EventRepo
        eventRepo = new EventRepo();

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        /*
        setSupportActionBar(toolbar);
        */



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get references to the UI elements

        EditText editTextEventName = findViewById(R.id.editTextEventName);
        EditText editTextEventIntro = findViewById(R.id.editTextEventIntro);
        EditText editTextOrgId = findViewById(R.id.editTextOrgId);
        EditText editTextLoc = findViewById(R.id.editTextLoc);
        EditText editTextEventYear = findViewById(R.id.editTextEventYear);
        EditText editTextEventMonth = findViewById(R.id.editTextEventMonth);
        EditText editTextEventDay = findViewById(R.id.editTextEventDay);
        EditText editTextEventHour = findViewById(R.id.editTextEventHour);
        EditText editTextEventMinute = findViewById(R.id.editTextEventMinute);
        EditText editTextEventTime = findViewById(R.id.editTextEventTime);
        //EditText editTextImagePath = findViewById(R.id.editTextImagePath);
        Button buttonSaveEvent = findViewById(R.id.buttonSaveEvent);


        // Set click listener for the Save button

        buttonSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect data from the input fields
                String orgId = editTextOrgId.getText().toString();
                String eventName = editTextEventName.getText().toString();
                String eventIntro = editTextEventIntro.getText().toString();

                String loc = editTextLoc.getText().toString();
                String eventYear = editTextEventYear.getText().toString();
                String eventMonth = editTextEventMonth.getText().toString();
                String eventDay = editTextEventDay.getText().toString();
                String eventHour = editTextEventHour.getText().toString();
                String eventMinute = editTextEventMinute.getText().toString();
                String eventTime = editTextEventTime.getText().toString();
                //String imagePath = editTextImagePath.getText().toString();

                // Call saveEvent method from EventRepo
                eventRepo.saveEvent(executorService, orgId, eventName, eventIntro, loc, eventYear, eventMonth,eventDay, eventHour, eventMinute, eventTime/*imagePath*/, uiHandler);

                Intent intent = new Intent(SaveEventActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });



    }
}