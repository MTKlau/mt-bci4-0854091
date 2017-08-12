package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;

import org.json.JSONArray;

/**
 * Created by Kalok on 9-8-2017.
 */

public class GetCallDataTask extends AsyncTask<Object,Object,JSONArray> {

    public GetCallDataTask(){
        
    }

    @Override
    protected JSONArray doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);


    }
}
