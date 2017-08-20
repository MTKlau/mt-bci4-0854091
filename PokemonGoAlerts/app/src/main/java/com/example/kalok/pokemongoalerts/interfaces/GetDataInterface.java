package com.example.kalok.pokemongoalerts.interfaces;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Kalok on 13-8-2017.
 */

public interface GetDataInterface {
    void processFinish(JSONArray jsonArray) throws JSONException;
}
