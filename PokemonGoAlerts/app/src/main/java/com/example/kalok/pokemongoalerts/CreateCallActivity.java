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
import android.widget.EditText;

public class CreateCallActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences getSharedPreferences;
    private int userId;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button continueToMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_call);
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

        titleEditText = (EditText) findViewById(R.id.callTitleEditText);
        descriptionEditText = (EditText) findViewById(R.id.callDescriptionEditText);
        continueToMapButton = (Button) findViewById(R.id.continueToMapButton);
        continueToMapButton.setOnClickListener(this);
    }

    private void createCall(){
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Intent continueToMapIntent = new Intent(CreateCallActivity.this,MapsActivity.class);
        continueToMapIntent.putExtra("title",title);
        continueToMapIntent.putExtra("description",description);
        startActivity(continueToMapIntent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.continueToMapButton:
                createCall();
                break;
            default:
                break;
        }
    }
}
