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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences getSharedPreferences;
    private Button goToMapsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

        getSharedPreferences  = this.getSharedPreferences(MainActivity.STATIC_PREFERENCES, MainActivity.MODE_PRIVATE);

        if(getSharedPreferences.contains("username") && getSharedPreferences.contains("level") && getSharedPreferences.contains("team")) {
            Log.d("USERNAME", getSharedPreferences.getString("username", null) + "");
            Log.d("LEVEL", getSharedPreferences.getInt("level", 0) + "");
            Log.d("TEAM", getSharedPreferences.getString("team", null) + "");
        }


        goToMapsButton = (Button) findViewById(R.id.goToMapsButton);
        goToMapsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.goToMapsButton):
                Intent mapIntent = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(mapIntent);
                break;
            default:
                break;
        }
    }
}
