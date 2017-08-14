package com.example.kalok.pokemongoalerts;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kalok.pokemongoalerts.interfaces.GetCalls;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GetCalls {

    private GoogleMap mMap;
    private String api = "https://stud.hosted.hr.nl/0854091/pogoalerts/";
    private GetCalls thisObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        thisObject = this;

        AsyncTask getCallDataTask = new GetCallDataTask("https://stud.hosted.hr.nl/0854091/usersdata/piet",this);
        getCallDataTask.execute();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(51.924420, 4.477733);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void processFinish(JSONArray jsonArray) {
        try{

            Log.d("test","test");
            Log.w("log",jsonArray+"");

//            for(int i = 0; i<jsonArray.length(); i++) {
//                Double latitude = jsonArray.getJSONObject(i).getDouble("latitude");
//                Double longitude = jsonArray.getJSONObject(i).getDouble("longitude");
//                LatLng markerPosition = new LatLng(latitude, longitude);
//
////                mMap.addMarker(new MarkerOptions().position(markerPosition).title(jsonArray.getJSONObject(i).getString("title")));
//            }
        }finally{
            Log.d("finally","finally");
        }
    }
}
