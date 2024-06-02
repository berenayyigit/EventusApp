package com.example.eventusapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventusapp.databinding.FragmentDetailsBinding;

import java.util.concurrent.ExecutorService;

public class FragmentDetails extends Fragment {

    FragmentDetailsBinding binding; // Declare the binding variable

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.d("FragmentDetails", "Handler received a message: " + msg.toString()); // Log when handler is called
            Event event = (Event) msg.obj;
            Log.d("FragmentDetails", "Event: " + event);

            if (event != null) {
                Log.d("FragmentDetails", "Event is not null");

                // Use the binding to access views and set data
                binding.txtEventName.setText(event.getName());
                binding.txtEventIntro.setText(event.getIntro());
                binding.txtEventDate.setText(event.getEventTime());
                binding.txtEventLoc.setText(event.getLoc());

                ((MainActivity) getActivity()).getToolBar().setTitle(event.getName());
            } else {
                Log.e("FragmentDetails", "Received null event");
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("FragmentDetails", "onCreateView called"); // Log when onCreateView is called
        // Inflate the layout and get an instance of the binding class
        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        // Retrieve the event ID from the arguments
        if (getArguments() != null) {
            String eventId = getArguments().getString("eventId");
            Log.d("FragmentDetails", "Received Event ID: " + eventId);

            // Fetch the event data by ID
            EventRepo repo = new EventRepo();
            ExecutorService srv = ((EventApplication) getActivity().getApplication()).srv;
            repo.getDataById(srv, handler, eventId);
            Log.d("FragmentDetails", "Service: " + srv);

        } else {
            Log.e("FragmentDetails", "No Event ID found in arguments");
        }

        // Return the root view of the binding
        return binding.getRoot();
    }
}
