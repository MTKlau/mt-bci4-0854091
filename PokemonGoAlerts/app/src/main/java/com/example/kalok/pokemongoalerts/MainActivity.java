package com.example.kalok.pokemongoalerts;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kalok.pokemongoalerts.interfaces.GetCalls;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int uniqueID = 345345;

    public static final String STATIC_PREFERENCES = "preferences";

    private SharedPreferences.Editor sharedEditor;
    private SharedPreferences getSharedPreferences;

    private EditText usernameEditText;
    private EditText levelEditText;
    private Spinner teamSpinner;
    private Button continueButton;
    private NotificationCompat.Builder notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        levelEditText = (EditText) findViewById(R.id.levelEditText);
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);
        continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(this);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("Ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Content title");
        notification.setContentText("Content body");

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID,notification.build());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sharedEditor = this.getSharedPreferences(STATIC_PREFERENCES, this.MODE_PRIVATE).edit();
        getSharedPreferences  = this.getSharedPreferences(STATIC_PREFERENCES, this.MODE_PRIVATE);

        if(getSharedPreferences.contains("username") && getSharedPreferences.contains("level") && getSharedPreferences.contains("team")){
            Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("view",v+"");
        switch(v.getId()){
            case R.id.continueButton:
                Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);

                sharedEditor.putString("username",usernameEditText.getText().toString());
                sharedEditor.putInt("level",Integer.parseInt(levelEditText.getText().toString()));
                sharedEditor.putString("team",teamSpinner.getSelectedItem().toString());
                sharedEditor.commit();

                AsyncTask createUserTask = new CreateUserTask(getString(R.string.api_users),usernameEditText.getText().toString(),Integer.parseInt(levelEditText.getText().toString()),teamSpinner.getSelectedItem().toString());
                createUserTask.execute();

                Log.d("shared",getSharedPreferences.getAll()+"");

                startActivity(homeIntent);
                break;
            default:
                Log.d("default","default");
                break;
        }
    }
}
