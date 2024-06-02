package com.example.eventusapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context ctx;
    List<Event> data;

    public EventAdapter(Context ctx, List<Event> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.event_row_layout, parent, false);
        EventViewHolder holder = new EventViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
            public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
                Event event = data.get(position);

                holder.txtEventName.setText(event.getName());
                holder.txtEventDate.setText(event.getEventDate());
                holder.txtEventTime.setText(event.getEventTime());

        ExecutorService srv = ((EventApplication)((MainActivity) ctx).getApplication()).srv;
                Log.d("MyApp", "ExecutorService obtained successfully.");

                holder.row.setOnClickListener(v -> {
                    Log.d("MyApp", "Row clicked.");
                    NavController navController = Navigation.findNavController((MainActivity) ctx, R.id.fragmentContainer);
                    Log.d("MyApp", "NavController obtained.");

                    String eventId = String.valueOf(data.get(holder.getBindingAdapterPosition()).getId());

                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("eventId", eventId);
                    Log.d("MyApp", "Event ID added to dataBundle.");
            Log.d("MyApp", "Event ID: " + eventId);

            navController.navigate(R.id.action_fragmentListEvent_to_FragmentDetails, dataBundle);
            Log.d("MyApp", "Navigating to FragmentDetails.");
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout row;
        TextView txtEventName;
        TextView txtEventDate;
        TextView txtEventTime;
        TextView txtEventLoc;
        TextView txtEventIntro;
        ImageView imgEvent;
        boolean imageDownloaded;

        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                imgEvent.setImageBitmap((Bitmap) msg.obj);
                imageDownloaded = true;
                return true;
            }
        });

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.row_list);
            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventDate = itemView.findViewById(R.id.txtEventDate);
            txtEventTime = itemView.findViewById(R.id.txtEventTime);
            txtEventLoc = itemView.findViewById(R.id.txtEventLoc);
            txtEventIntro = itemView.findViewById(R.id.txtEventIntro);
            imgEvent = itemView.findViewById(R.id.imgEvent);
        }
    }
}