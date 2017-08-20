package com.example.kalok.pokemongoalerts;

import android.content.Intent;
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

import com.example.kalok.pokemongoalerts.interfaces.GetDataInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements GetDataInterface,AdapterView.OnItemClickListener{

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

        AsyncTask getCallDataTask = new GetCallDataTask("https://stud.hosted.hr.nl/0854091/pogoalerts/calls/", this);
        getCallDataTask.execute();
    }

    @Override
    public void processFinish(JSONArray jsonArray) {

        try{
            for(int i=0;i<jsonArray.length();i++){
                JSONObject call = jsonArray.getJSONObject(i);

                Log.d("call",call.getInt("id")+"");
                Log.d("user",call.getInt("user_id")+"");
                int id = call.getInt("id");
                int userId = call.getInt("user_id");
                String title = call.getString("title");
                String description = call.getString("description");
                double latitude = call.getDouble("latitude");
                double longitude = call.getDouble("longitude");

                Bundle callsBundle = new Bundle();
                callsBundle.putInt("id",id);
                callsBundle.putInt("user_id",userId);
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

        Log.d("BUNDLE",bundlesList.get(position).getDouble("latitude")+"");

        bundlesList.get(position).get("title");

        Intent callDetailIntent = new Intent(OverviewActivity.this,CallDetailActivity.class);
        callDetailIntent.putExtra("id",bundlesList.get(position).getInt("id"));
        callDetailIntent.putExtra("user_id",bundlesList.get(position).getInt("user_id"));
        callDetailIntent.putExtra("title",bundlesList.get(position).get("title").toString());
        callDetailIntent.putExtra("description",bundlesList.get(position).get("description").toString());
        callDetailIntent.putExtra("latitude",bundlesList.get(position).getDouble("latitude"));
        callDetailIntent.putExtra("longitude",bundlesList.get(position).getDouble("longitude"));
        startActivity(callDetailIntent);
    }
}
