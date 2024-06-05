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

public class DeleteOrgActivity extends AppCompatActivity {

    private ExecutorService executorService;
    private Handler uiHandler;
    private OrgRepo orgRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_org);

        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // For example, show a Toast message with the result
                String result = (String) msg.obj;
                Toast.makeText(DeleteOrgActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Initialize OrgRepo
        orgRepo = new OrgRepo();

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get reference to the UI element
        EditText editTextOrgId = findViewById(R.id.editTextOrgId);
        Button buttonDeleteOrg = findViewById(R.id.buttonDeleteOrg);


        // Set click listener for the Delete button
        buttonDeleteOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the organization ID from the input field
                String orgId = editTextOrgId.getText().toString();

                // Call deleteOrg method from OrgRepo
                orgRepo.deleteOrg(executorService, orgId, uiHandler);

                Intent intent = new Intent(DeleteOrgActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
