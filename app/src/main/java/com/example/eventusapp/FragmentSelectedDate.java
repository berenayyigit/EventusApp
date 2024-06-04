package com.example.eventusapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentSelectedDate extends Fragment {

    private RecyclerView recyclerView;
    private TextView textViewEmpty;
    private List<Event> allEvents;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.selected_date_list, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_events);
        textViewEmpty = root.findViewById(R.id.text_view_empty);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("selectedDate")) {
            String selectedDate = bundle.getString("selectedDate");
            List<Parcelable> parcelableEvents = bundle.getParcelableArrayList("allEvents");
            List<Event> allEvents = new ArrayList<>();
            if (parcelableEvents != null) {
                for (Parcelable parcelable : parcelableEvents) {
                    if (parcelable instanceof Event) {
                        allEvents.add((Event) parcelable);
                    }
                }
            }
            setAllEvents(allEvents);
            filterEventsByDate(selectedDate);
        }
    }




    private void filterEventsByDate(String selectedDate) {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getEventDate().equals(selectedDate)) {
                filteredEvents.add(event);
            }
        }

        if (filteredEvents.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.GONE);

            // Setup RecyclerView with EventAdapter
            EventAdapter eventAdapter = new EventAdapter(getContext(), filteredEvents);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(eventAdapter);
        }
    }

    // Setter method to set allEvents list
    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }
}

