package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;

import com.example.kalok.pokemongoalerts.interfaces.GetDataInterface;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Kalok on 9-8-2017.
 */

public class GetCallDataTask extends AsyncTask<Object,Void,JSONArray> {

    private String url;
    private GetDataInterface getDataInterface;

    public GetCallDataTask(String url,GetDataInterface getCalls){
        this.url = url;
        this.getDataInterface = getCalls;
    }

    @Override
    protected JSONArray doInBackground(Object[] params) {
        GetData getCallData = new GetData();
        return getCallData.getJsonData(this.url);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);

        try {
            getDataInterface.processFinish(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
