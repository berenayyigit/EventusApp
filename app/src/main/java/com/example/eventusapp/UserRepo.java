package com.example.eventusapp;

import android.os.Handler;
import android.os.Message;

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
    public void sayHelloJSON(ExecutorService srv, Handler uiHandler, String name, String lastname){

        //Gson (Google's JSON package)

        srv.execute(()->{

            try {
                URL url = new URL("http://10.3.0.14:8080/helloapi/sayhello/helloperson");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");

                JSONObject objToSend = new JSONObject();
                objToSend.put("name",name);
                objToSend.put("lastname",lastname);

                //String outputData = "{\"name\":\""+ name +"\",\"lastname\":\""+ lastname+"\"}";

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());

                writer.write(objToSend.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }


                JSONObject obj = new JSONObject(buffer.toString());

                String strDate = obj.getString("date");
                String strName = obj.getString("fullname");

                String retVal = "Date is " + strDate + ", and fullname is " + strName;

                Message msg = new Message();
                msg.obj = retVal;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });


    }


}
