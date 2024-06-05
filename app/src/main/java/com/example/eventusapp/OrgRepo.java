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

public class OrgRepo {


    public void saveOrg(ExecutorService srv, String name, Handler uiHandler){
        srv.execute(()->{

            try {
                URL url = new URL("http://10.0.2.2:8080/ourevents/organizations/save");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();


                outputData.put("name", name);

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
    public void deleteOrg(ExecutorService srv, String organizationId, Handler uiHandler) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/ourevents/organizations/delete/" + organizationId);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject retVal = new JSONObject(buffer.toString());
                conn.disconnect();

                String retValStr = retVal.getString("result");

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
    public void updateOrg(ExecutorService srv, String organizationId, String name, Handler uiHandler) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/ourevents/organizations/update/" + organizationId);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject outputData = new JSONObject();
                outputData.put("name", name);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject retVal = new JSONObject(buffer.toString());
                conn.disconnect();

                Message msg = new Message();
                msg.obj = retVal.toString(); // You can change this based on what you need to pass to the handler
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
