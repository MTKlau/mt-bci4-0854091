package com.example.kalok.pokemongoalerts;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kalok on 12-8-2017.
 */

public class GetCallData {

    private HttpURLConnection getUrlConnection;
    private BufferedReader bufferedReader;
    private JSONArray jsonArray;

    public GetCallData(){

    }

    public JSONArray getJsonData(String url){

        try{
            URL getUrl = new URL(url);
            this.getUrlConnection = (HttpURLConnection) getUrl.openConnection();
            this.getUrlConnection.connect();

            InputStream getInputStream = getUrlConnection.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(getInputStream));
            String jsonLine = "";
            StringBuffer jsonBuffer = new StringBuffer();

            while((jsonLine = bufferedReader.readLine()) != null){
                jsonBuffer.append(jsonLine);
            }

            String jsonData = jsonBuffer.toString();
            this.jsonArray = new JSONArray(jsonData);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        } finally{
            if(this.getUrlConnection != null){
                this.getUrlConnection.disconnect();
            }
            try{
                if(this.bufferedReader != null){
                    this.bufferedReader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return this.jsonArray;
    }

}
