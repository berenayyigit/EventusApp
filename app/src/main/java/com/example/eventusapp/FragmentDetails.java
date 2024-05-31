package com.example.eventusapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventusapp.databinding.FragmentDetailsBinding;

import java.util.concurrent.ExecutorService;

public class FragmentDetails extends Fragment {

    FragmentDetailsBinding binding;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Event event = (Event) msg.obj;
            binding.txtNameDetail.setText(event.getName());
            binding.textIntro.setText(event.getIntro());

            ((MainActivity) getActivity()).getToolBar().setTitle(event.getName());

            EventRepo repo = new EventRepo();
            ExecutorService srv = ((EventApplication) getActivity().getApplication()).srv;


            return true;
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());

        int eventId = getArguments().getInt("eventId");

        EventRepo repo = new EventRepo();
        ExecutorService srv = ((EventApplication) getActivity().getApplication()).srv;
        repo.getDataById(srv, handler, eventId);

        return binding.getRoot();
    }
}