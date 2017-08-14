package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;

import com.example.kalok.pokemongoalerts.interfaces.GetCalls;

import org.json.JSONArray;

/**
 * Created by Kalok on 9-8-2017.
 */

public class GetCallDataTask extends AsyncTask<Object,Void,JSONArray> {

    private String url;
    private GetCalls getCalls;

    public GetCallDataTask(String url,GetCalls getCalls){
        this.url = url;
        this.getCalls = getCalls;
    }

    @Override
    protected JSONArray doInBackground(Object[] params) {
        GetCallData getCallData = new GetCallData();
        return getCallData.getJsonData(this.url);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);

        getCalls.processFinish(jsonArray);
    }
}
