package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kalok on 13-8-2017.
 */

public class CreateCallTask extends AsyncTask<Object, Object, Object> {

    private String url;
    private int userId;
    private String title;
    private String description;
    private double latitude;
    private double longitude;

    public CreateCallTask(String url, int userId,String title,String description,double latitude,double longitude){
        this.url = url;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            URL url = new URL(this.url);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept","application/json");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("user_id",this.userId);
            jsonParam.put("title", this.title);
            jsonParam.put("description", this.description);
            jsonParam.put("latitude", this.latitude);
            jsonParam.put("longitude", this.longitude);
            
            Log.i("JSON", jsonParam.toString());

            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG" , connection.getResponseMessage());

            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
