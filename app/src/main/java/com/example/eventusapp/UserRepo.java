package com.example.eventusapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
import java.util.concurrent.ExecutorService;

public class UserRepo {
    public void saveUser(ExecutorService srv, String username, String password, String name, String lastName, Handler uiHandler){
        srv.execute(()->{

            try {
                URL url = new URL("http://10.0.2.2:8080/user/register");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("username", username);
                outputData.put("password", password);
                outputData.put("name", name);
                outputData.put("lastName", lastName);

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
    public void checkLogin(String username, String password, ExecutorService executorService, Handler uiHandler) {
        executorService.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/user/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject loginData = new JSONObject();
                loginData.put("username", username);
                loginData.put("password", password);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(loginData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }


                // Log raw response data
                Log.d("UserRepo", "Raw Response Data: " + buffer.toString());


                JSONObject response = new JSONObject(buffer.toString());
                String result = response.getString("status");

                // Log the server response
                Log.d("LoginActivity", "Server Response: " + result);

                Message msg = new Message();
                msg.obj = result;
                uiHandler.sendMessage(msg);

                conn.disconnect();
            } catch (Exception e) {
                Log.e("LoginError", "Error during login", e);

                // Send an error message to the UI handler
                Message msg = new Message();
                msg.obj = "Login Failed";
                uiHandler.sendMessage(msg);
            }
        });
    }
}


