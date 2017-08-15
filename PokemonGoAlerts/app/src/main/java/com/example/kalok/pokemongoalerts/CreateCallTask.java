package com.example.kalok.pokemongoalerts;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kalok.pokemongoalerts.models.Call;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kalok on 13-8-2017.
 */

public class CreateCallTask extends AsyncTask<Object, Object, Object> {

    private Call call;

    public CreateCallTask(){

    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            URL url = new URL("https://stud.hosted.hr.nl/0854091/pogoalerts/users/");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();


            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept","application/json");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("title", "Zapdos raid");
//            jsonParam.put("description", "Mensen gezocht voor Zapdos raid in Rotterdam Centrum!");
//            jsonParam.put("latitude", "52.2848394");
//            jsonParam.put("longitude", "4.4345342");
            jsonParam.put("username","KLau88");
            jsonParam.put("level",34);
            jsonParam.put("team","Valor");

            Log.i("JSON", jsonParam.toString());
            Log.d("ROFLMFAO","THIS IS TOO FUNNY");
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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
