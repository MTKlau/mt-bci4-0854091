package com.example.kalok.pokemongoalerts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kalok.pokemongoalerts.interfaces.GetDataInterface;
import com.example.kalok.pokemongoalerts.interfaces.GetUserInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CallDetailActivity extends AppCompatActivity implements View.OnClickListener,GetDataInterface,GetUserInterface {

    private Bundle data;
    private TextView callTitleTextView;
    private TextView callDescriptionTextView;
    private TextView callParticipantsTextView;
    private Button joinLobbyButton;
    private Button showCallLocationButton;
    private TableLayout participantsTable;
    private AsyncTask getUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = getIntent().getExtras();

        callTitleTextView = (TextView) findViewById(R.id.callTitleTextView);
        callDescriptionTextView = (TextView) findViewById(R.id.callDescriptionTextView);
        callParticipantsTextView = (TextView) findViewById(R.id.callParticipantsTextView);

        callTitleTextView.setText(data.get("title").toString());
        callDescriptionTextView.setText(data.get("description").toString());

        showCallLocationButton = (Button) findViewById(R.id.showCallLocationButton);
        showCallLocationButton.setOnClickListener(this);

        joinLobbyButton = (Button) findViewById(R.id.joinLobbyButton);
        joinLobbyButton.setOnClickListener(this);

        participantsTable = (TableLayout) findViewById(R.id.participantsTable);

        AsyncTask getParticipantsTask = new GetParticipantsTask("https://stud.hosted.hr.nl/0854091/pogoalerts/call_user/",this);
        getParticipantsTask.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.showCallLocationButton){

            Intent goToMapIntent = new Intent(CallDetailActivity.this,MapsActivity.class);
            goToMapIntent.putExtra("latitude",data.getDouble("latitude"));
            goToMapIntent.putExtra("longitude",data.getDouble("longitude"));
            startActivity(goToMapIntent);
        }

        if(v.getId() == R.id.joinLobbyButton){
            AsyncTask createCallUserTask = new CreateCallUserTask("https://stud.hosted.hr.nl/0854091/pogoalerts/call_user/",data.getInt("id"),data.getInt("user_id"));
            createCallUserTask.execute();

            Intent reloadIntent = new Intent(CallDetailActivity.this, CallDetailActivity.class);
            startActivity(reloadIntent);
        }
    }

    @Override
    public void processFinish(JSONArray jsonArray) {

        try{
            for(int i = 0; i<jsonArray.length();i++){
                JSONObject participant = jsonArray.getJSONObject(i);

                getUserTask = new GetUserDataTask("https://stud.hosted.hr.nl/0854091/pogoalerts/users/"+participant.get("user_id"),this);
                getUserTask.execute();

            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getUserData(JSONArray jsonArray) {

        try{
            for(int i = 0; i<jsonArray.length();i++){
                JSONObject participant = jsonArray.getJSONObject(i);

                callParticipantsTextView.append(participant.get("level") + " " + participant.get("username") + " " + participant.get("team") +"\r\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}