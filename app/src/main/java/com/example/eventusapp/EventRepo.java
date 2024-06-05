package com.example.eventusapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class EventRepo {


    public void getAllEvents(ExecutorService srv, Handler uiHandler){


        srv.submit(()->{
            try {

                List<Event> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/ourevents/events/sort");


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

                    String eventDate = dateObj.getString("year") + "-" + dateObj.getString("month") + "-" + dateObj.getString("day");
                    String eventTime = dateObj.getString("hour") + "-" + dateObj.getString("minute");

                    Event myEvent = new Event(
                            current.getString("id"),
                            current.getString("name"),
                            current.getString("intro"),
                            current.getJSONObject("org").getString("name"),
                            current.getJSONObject("loc").getString("location"),
                            eventDate,
                            eventTime
                            // current.getString("imageUrl")

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
                URL url = new URL("http://10.0.2.2:8080/ourevents/events/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                JSONObject current = new JSONObject(buffer.toString());

                JSONObject dateObj = current.getJSONObject("date");

                String eventDate = dateObj.getString("year") + "-" + dateObj.getString("month") + "-" + dateObj.getString("day");
                String eventTime = dateObj.getString("hour") + "-" + dateObj.getString("minute");

                Event myEvent = new Event(
                        current.getString("id"),
                        current.getString("name"),
                        current.getString("intro"),
                        current.getJSONObject("org").getString("name"),
                        current.getJSONObject("loc").getString("location"),
                        eventDate,
                        eventTime
                        // current.getString("imageUrl")
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
    public void saveEvent(ExecutorService srv, String orgid, String name, String intro, String loc, String year, String month, String day, String hour, String minute, String eventime, Handler uiHandler){
        srv.execute(()->{

            try {
                URL url = new URL("http://10.0.2.2:8080/ourevents/events/save");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                // Create the nested date JSON object
                JSONObject dateObject = new JSONObject();
                dateObject.put("year", year);
                dateObject.put("month", month);
                dateObject.put("day", day);
                dateObject.put("hour", hour);
                dateObject.put("minute", minute);
                dateObject.put("time", year + "-" + month + "-" + day + "-" + hour + "-" + minute);

                // Create the main JSON object
                JSONObject outputData = new JSONObject();
                outputData.put("orgid", orgid);
                outputData.put("name", name);
                outputData.put("intro", intro);
                outputData.put("loc", loc);
                outputData.put("date", dateObject);
                //outputData.put("imagePath", imagePath);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());
                conn.disconnect();


                String retValStr = retVal.getString("result");

                //Update Listener or handlers, it is for you to complete!

                Message msg = new Message();
                msg.obj = retValStr;
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
    public void deleteEvent(ExecutorService srv, String eventId, Handler uiHandler) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/ourevents/events/delete/" + eventId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("DELETE");

                int responseCode = conn.getResponseCode();

                String message;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    message = "Event deleted successfully";
                } else {
                    message = "Failed to delete event";
                }

                // Update UI with the result
                Message msg = new Message();
                msg.obj = message;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}