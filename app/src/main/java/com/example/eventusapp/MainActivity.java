package com.example.eventusapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Entry point to an activity
        //1) Load the layout
        //2) Interact with UI components

        setContentView(R.layout.layout_main_activity);
    }
}
