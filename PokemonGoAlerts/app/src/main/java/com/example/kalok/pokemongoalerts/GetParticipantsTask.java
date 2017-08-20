package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;

import com.example.kalok.pokemongoalerts.interfaces.GetDataInterface;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Kalok on 20-8-2017.
 */

public class GetParticipantsTask extends AsyncTask<Object,Object,JSONArray> {

    private String url;
    private GetDataInterface getDataInterface;

    public GetParticipantsTask(String url,GetDataInterface getDataInterface){
        this.url = url;
        this.getDataInterface = getDataInterface;
    }

    @Override
    protected JSONArray doInBackground(Object[] params) {
        GetData getParticipants = new GetData();
        return getParticipants.getJsonData(this.url);
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
