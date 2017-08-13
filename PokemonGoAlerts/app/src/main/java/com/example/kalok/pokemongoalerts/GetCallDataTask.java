package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;

import org.json.JSONArray;

/**
 * Created by Kalok on 9-8-2017.
 */

public class GetCallDataTask extends AsyncTask<Object,Object,JSONArray> {

    private String url;

    public GetCallDataTask(String url){
        this.url = url;
    }

    @Override
    protected JSONArray doInBackground(Object[] params) {
        GetCallData getCallData = new GetCallData();
        return getCallData.getJsonData(this.url);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);

        
    }
}
