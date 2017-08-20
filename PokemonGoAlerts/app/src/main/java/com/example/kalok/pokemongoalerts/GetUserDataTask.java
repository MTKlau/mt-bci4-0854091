package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;

import com.example.kalok.pokemongoalerts.interfaces.GetDataInterface;
import com.example.kalok.pokemongoalerts.interfaces.GetUserInterface;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Kalok on 20-8-2017.
 */

public class GetUserDataTask extends AsyncTask<Object,Object,JSONArray> {

    private String url;
    private GetUserInterface getUserInterface;

    public GetUserDataTask(String url,GetUserInterface getUserInterface){
        this.url = url;
        this.getUserInterface = getUserInterface;
    }

    @Override
    protected JSONArray doInBackground(Object... params) {
        GetData getUsers = new GetData();
        return getUsers.getJsonData(this.url);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);

        try {
            getUserInterface.getUserData(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
