package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kalok.pokemongoalerts.interfaces.GetCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements GetCalls,AdapterView.OnItemClickListener{

    private ListView callsListView;
    private ArrayList<String> callsList;
    private ArrayList<Bundle> bundlesList;
    private ArrayAdapter callsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        callsListView = (ListView) findViewById(R.id.callsListView);
        callsList = new ArrayList<String>();
        bundlesList = new ArrayList<Bundle>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AsyncTask getCallDataTask = new GetCallDataTask("https://stud.hosted.hr.nl/0854091/pogoalerts/calls/", this);
        getCallDataTask.execute();
    }

    @Override
    public void processFinish(JSONArray jsonArray) {

        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject call = jsonArray.getJSONObject(i);

                String title = call.getString("title");
                String description = call.getString("description");
                double latitude = call.getDouble("latitude");
                double longitude = call.getDouble("longitude");

                Bundle callsBundle = new Bundle();
                callsBundle.putString("title",title);
                callsBundle.putString("description",description);
                callsBundle.putDouble("latitude",latitude);
                callsBundle.putDouble("longitude",longitude);
                bundlesList.add(callsBundle);
                callsList.add(title);
            }

            ArrayAdapter callsAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,callsList);
            callsListView.setAdapter(callsAdapter);
            callsListView.setOnItemClickListener(this);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
