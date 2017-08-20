package com.example.kalok.pokemongoalerts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences getSharedPreferences;
    private Button createGroupButton;
    private Button goToOverviewButton;
    private Button goToMapsButton;
    private TextView homeTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeTitleTextView = (TextView) findViewById(R.id.homeTitleTextView);

        getSharedPreferences  = this.getSharedPreferences(MainActivity.STATIC_PREFERENCES, MainActivity.MODE_PRIVATE);

        if(getSharedPreferences.contains("username") && getSharedPreferences.contains("level") && getSharedPreferences.contains("team")) {
            homeTitleTextView.append(" "+getSharedPreferences.getString("username", null));
//            Log.d("USERNAME", getSharedPreferences.getString("username", null) + "");
            Log.d("LEVEL", getSharedPreferences.getInt("level", 0) + "");
            Log.d("TEAM", getSharedPreferences.getString("team", null) + "");
        }

        createGroupButton = (Button) findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(this);

        goToOverviewButton = (Button) findViewById(R.id.goToOverviewButton);
        goToOverviewButton.setOnClickListener(this);

        goToMapsButton = (Button) findViewById(R.id.goToMapsButton);
        goToMapsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.createGroupButton):
                Intent createCallIntent = new Intent(HomeActivity.this,CreateCallActivity.class);
                startActivity(createCallIntent);
                break;
            case(R.id.goToOverviewButton):
                Intent overviewIntent = new Intent(HomeActivity.this,OverviewActivity.class);
                startActivity(overviewIntent);
                break;
            case(R.id.goToMapsButton):
                Intent mapIntent = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(mapIntent);
                break;
            default:
                break;
        }
    }
}
