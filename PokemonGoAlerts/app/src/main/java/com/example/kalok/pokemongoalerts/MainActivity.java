package com.example.kalok.pokemongoalerts;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.kalok.pokemongoalerts.interfaces.GetCalls;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LocationSource.OnLocationChangedListener, View.OnClickListener, GetCalls{

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int uniqueID = 345345;

    private EditText usernameEditText;
    private EditText levelEditText;
    private EditText teamEditText;
    private Button continueButton;
    private NotificationCompat.Builder notification;

    private boolean isNetworkEnabled;
    private Location location;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        levelEditText = (EditText) findViewById(R.id.levelEditText);
        teamEditText = (EditText) findViewById(R.id.teamEditText);
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

//        AsyncTask createUserTask = new CreateCallTask();
//        createUserTask.execute();
    }

    public void askForLocationPermission() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_FINE_LOCATION);
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location != null){
                Log.d("latitude",location.getLatitude()+"");
                Log.d("longitude",location.getLongitude()+"");
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("latitude",location.getLatitude()+"");
        Log.d("longitude",location.getLongitude()+"");
    }

    @Override
    public void onClick(View v) {
        Log.d("view",v+"");
        switch(v.getId()){
            case R.id.continueButton:
                Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);

                AsyncTask createUserTask = new CreateUserTask("https://stud.hosted.hr.nl/0854091/pogoalerts/users/",usernameEditText.getText().toString(),Integer.parseInt(levelEditText.getText().toString()),teamEditText.getText().toString());
                createUserTask.execute();

                startActivity(homeIntent);
                break;
            default:
                Log.d("default","default");
                break;
        }
    }

//    public static SecretKey generateKey()
//            throws NoSuchAlgorithmException, InvalidKeySpecException {
//        return secret = new SecretKeySpec(password.getBytes(), "AES");
//    }

    public static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
   /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException
    {
    /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    @Override
    public void processFinish(JSONArray jsonArray) {

    }
}
