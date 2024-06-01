package com.example.eventusapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{



    Context ctx;
    List<Event> data;

    public EventAdapter(Context ctx, List<Event> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(ctx).inflate(R.layout.event_row_layout,parent,false);
        EventViewHolder holder = new EventViewHolder(root);

        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        holder.txtName.setText(data.get(position).getName());

        ExecutorService srv = ((EventApplication)((Activity)ctx).getApplication()).srv;

        //holder.downloadImage(srv,data.get(position).getImagePath());

        holder.row.setOnClickListener(v->{

            NavController navController =
                    Navigation.findNavController((Activity) ctx,R.id.fragmentContainer);

            Bundle dataBundle = new Bundle();
            dataBundle.putString("eventId", String.valueOf(data.get(holder.getBindingAdapterPosition()).getId()));


            navController.navigate(R.id.action_fragmentListEvent_to_FragmentDetails,dataBundle);


        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout row;
        TextView txtName;
        ImageView imgOpSys;

        boolean imageDownloaded;


        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                imgOpSys.setImageBitmap((Bitmap) msg.obj);
                imageDownloaded = true;

                return true;
            }
        });


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.row_list);
            txtName = itemView.findViewById(R.id.txtEventName);

        }

    }


}
