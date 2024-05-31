package com.example.eventusapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import com.example.eventusapp.databinding.LayoutMainActivityBinding;
public class MainActivity extends AppCompatActivity {
    LayoutMainActivityBinding binding;
    Toolbar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutMainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHost = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        NavController navController = navHost.getNavController();

        AppBarConfiguration conf = new AppBarConfiguration.Builder(navController.getGraph()).build();

        toolBar = findViewById(R.id.toolbar);

        toolBar.setTitle("Events");
        NavigationUI.setupWithNavController(toolBar,navController,conf);

    }
    public Toolbar getToolBar() {
        return toolBar;
    }
}
