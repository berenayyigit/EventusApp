package com.example.eventusapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eventusapp.databinding.FragmentListEventBinding;

public class FragmentListEvent extends Fragment {

    private FragmentListEventBinding binding;
    private EventAdapter eventAdapter;
    private ExecutorService srv;

    public interface EventDataListener {
        void onEventDataReceived(List<Event> events);
    }

    public FragmentListEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListEventBinding.inflate(inflater, container, false);
        binding.recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventAdapter = new EventAdapter(getActivity(), new ArrayList<>());
        binding.recView.setAdapter(eventAdapter);

        srv = ((EventApplication) getActivity().getApplication()).srv;

        fetchData();

        return binding.getRoot();
    }

    private void fetchData() {
        EventRepo repo = new EventRepo();
        repo.getAllEvents(srv, new Handler(Looper.getMainLooper(), msg -> {
            List<Event> data = (List<Event>) msg.obj;
            updateEventList(data);
            if (getActivity() instanceof EventDataListener) {
                ((EventDataListener) getActivity()).onEventDataReceived(data);
            }
            return true;
        }));
    }

    private void updateEventList(List<Event> events) {
        if (events != null && eventAdapter != null) {
            eventAdapter.updateEvents(events);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
