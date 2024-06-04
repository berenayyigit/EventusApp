
package com.example.eventusapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventusapp.databinding.FragmentListEventBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class FragmentListEvent extends Fragment {

    FragmentListEventBinding binding;
    private EventDataListener eventDataListener;
    public interface EventDataListener {
        void onEventDataReceived(List<Event> events);
    }





    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Event> data = (List<Event>) msg.obj;
            EventAdapter adapter = new EventAdapter(getActivity(), data);
            binding.recView.setAdapter(adapter);
            return true;
        }
    });
    public FragmentListEvent() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListEventBinding.inflate(inflater, container, false);

        binding.recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        EventRepo repo = new EventRepo();
        ExecutorService srv = ((EventApplication) getActivity().getApplication()).srv;

        repo.getAllEvents(srv, new Handler(msg -> {
            List<Event> data = (List<Event>) msg.obj;
            EventAdapter adapter = new EventAdapter(getActivity(), data);
            binding.recView.setAdapter(adapter);

            // Pass the data to MainActivity
            if (getActivity() instanceof EventDataListener) {
                ((EventDataListener) getActivity()).onEventDataReceived(data);
            }
            return true;
        }));

        return binding.getRoot();
    }
}
