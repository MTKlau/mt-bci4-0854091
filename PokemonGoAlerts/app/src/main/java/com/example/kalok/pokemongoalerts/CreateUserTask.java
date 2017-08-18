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

public class CreateUserTask extends AsyncTask<Object,Object,Object> {

    private int userId;
    private String url;
    private String username;
    private int level;
    private String team;

    public CreateUserTask(String url, String username, int level, String team){
        this.url = url;
        this.username = username;
        this.level = level;
        this.team = team;
    }

    @Override
    protected Object doInBackground(Object[] params) {
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
            jsonParam.put("username",this.username);
            jsonParam.put("level",this.level);
            jsonParam.put("team",this.team);

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
