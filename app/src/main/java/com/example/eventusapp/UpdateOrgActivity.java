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

public class UpdateOrgActivity extends AppCompatActivity {

    private ExecutorService executorService;
    private Handler uiHandler;
    private OrgRepo orgRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_org);

        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);

        // Initialize Handler to process messages on the UI thread
        uiHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Handle the UI update here
                // For example, show a Toast message with the result
                String result = (String) msg.obj;
                Toast.makeText(UpdateOrgActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        orgRepo = new OrgRepo();

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        EditText editTextOrgId = findViewById(R.id.editTextOrgId);
        EditText editTextOrgName = findViewById(R.id.editTextOrgName);
        Button buttonUpdateOrg = findViewById(R.id.buttonUpdateOrg);

        buttonUpdateOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect data from the input fields
                String orgId = editTextOrgId.getText().toString();
                String orgName = editTextOrgName.getText().toString();

                // Call updateOrg method from OrgRepo
                orgRepo.updateOrg(executorService, orgId, orgName, uiHandler);

                // You might want to add an Intent to go back to the previous activity or any other navigation logic
                Intent intent = new Intent(UpdateOrgActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
