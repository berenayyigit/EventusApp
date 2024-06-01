package com.example.eventusapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class EventRepo {

    public void getAllEvents(ExecutorService srv, Handler uiHandler){


        srv.submit(()->{
            try {

                List<Event> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/ourevents/events");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);


                    JSONObject dateObj = current.getJSONObject("date");

                    Event myEvent = new Event(
                            current.getString("name"),
                            current.getString("intro"),
                            current.getString("org"),
                            current.getString("loc"),
                            dateObj.getString("time")
                    );

                    data.add(myEvent);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }


        });

    }
    public void getDataById(ExecutorService srv, Handler uiHandler,String id) {


        srv.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/ourevents/events" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                JSONObject current = new JSONObject(buffer.toString());






                JSONObject dateObj = current.getJSONObject("date");

                Event myEvent = new Event(
                        current.getString("name"),
                        current.getString("intro"),
                        current.getString("org"),
                        current.getString("loc"),
                        dateObj.getString("time")
                );


                Message msg = new Message();
                msg.obj = myEvent;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

}
