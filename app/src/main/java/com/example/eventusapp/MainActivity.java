package com.example.eventusapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.eventusapp.databinding.LayoutMainActivityBinding;
public class MainActivity extends AppCompatActivity implements FragmentListEvent.EventDataListener{
    LayoutMainActivityBinding binding;
    Toolbar toolBar;
    private MainActivity eventDataListener;
    private List<Event> allEvents ;
    private Button btnSelectDate;
    private Button buttonNavigateToSaveEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutMainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        NavController navController = navHost.getNavController();

        AppBarConfiguration conf = new AppBarConfiguration.Builder(navController.getGraph()).build();

        toolBar = findViewById(R.id.toolbar);

        toolBar.setTitle("Events");
        NavigationUI.setupWithNavController(toolBar, navController, conf);

        // btnSelectDate initialization and click listener
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        // Initialize buttonNavigateToSaveEvent and set click listener
        buttonNavigateToSaveEvent = findViewById(R.id.buttonNavigateToSaveEvent);
        buttonNavigateToSaveEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View v) {

                Intent intent = new Intent(MainActivity.this, SaveEventActivity.class);
                startActivity(intent);


            }
        });

    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    navigateToEventListFragment(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }



    @Override
    public void onEventDataReceived(List<Event> events) {
        // This method will be called when the list of events is received from FragmentListEvent
        allEvents = events;
    }
    private void navigateToEventListFragment(String selectedDate) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("selectedDate", selectedDate);

            // Convert ArrayList<Event> to ArrayList<Parcelable>
            ArrayList<Parcelable> parcelableEvents = new ArrayList<>();
            for (Event event : allEvents) {
                parcelableEvents.add((Parcelable) event); // Explicitly cast Event to Parcelable
            }
            bundle.putParcelableArrayList("allEvents", parcelableEvents); // Pass allEvents to FragmentSelectedDate
            Event firstEvent = (Event) parcelableEvents.get(0);
            String eventMessage = firstEvent.getName(); // Assuming getName() returns the name of the event as a String
            Toast.makeText(this, eventMessage, Toast.LENGTH_SHORT).show();
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            NavController navController = navHostFragment.getNavController();

            navController.navigate(R.id.action_fragmentListEvent_to_fragmentSelectedDate, bundle);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("NavigationError", "NullPointerException occurred: " + e.getMessage());
            Toast.makeText(this, "Navigation failed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("NavigationError", "An unexpected error occurred: " + e.getMessage());
            Toast.makeText(this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void filterEventsByDate(String date) {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getEventDate().equals(date)) {
                filteredEvents.add(event);
            }
        }

        // Assuming you have a Context instance available, such as 'this'
        EventAdapter eventAdapter = new EventAdapter(this, filteredEvents);

        eventAdapter.updateEvents(filteredEvents);
        Toast.makeText(this, "Selected Date: " + date, Toast.LENGTH_SHORT).show();
    }
    public Toolbar getToolBar() {
        return toolBar;
    }
}