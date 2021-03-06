package com.example.kalok.pokemongoalerts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kalok.pokemongoalerts.interfaces.GetDataInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GetDataInterface, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private String api = "https://stud.hosted.hr.nl/0854091/pogoalerts/calls/";
    private GetDataInterface thisObject;
    private Marker createMarker;
    private double latitude;
    private double longitude;

    private String callTitle;
    private String callDescription;

    private boolean isNetworkEnabled;
    private Location location;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        thisObject = this;

        askForLocationPermission();
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
        mMap.setOnMapClickListener(this);

        Bundle data = getIntent().getExtras();

        double latitude = data.getDouble("latitude");
        double longitude = data.getDouble("longitude");

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitude,longitude));

        if(Double.isNaN(latitude) && Double.isNaN(longitude)){
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(51.887077,4.490022)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.887077,4.490022),15));
        }else{
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15));
        }

        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        latitude = latLng.latitude;
        longitude = latLng.longitude;

        // Clears the previously touched position
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        createMarker = mMap.addMarker(markerOptions);

        Bundle data = getIntent().getExtras();

        if (data != null) {
            callTitle = data.getString("title");
            callDescription = data.getString("description");
            markerOptions.title(callTitle);
        }
        AsyncTask createCallTask = new CreateCallTask(api, 19, callTitle, callDescription, latitude, longitude);
        createCallTask.execute();


//        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void processFinish(JSONArray jsonArray) {
        try {

            Log.d("test", "test");
            Log.w("log", jsonArray + "");

            for (int i = 0; i < jsonArray.length(); i++) {
                Double latitude = jsonArray.getJSONObject(i).getDouble("latitude");
                Double longitude = jsonArray.getJSONObject(i).getDouble("longitude");
                LatLng markerPosition = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions().position(markerPosition).title(jsonArray.getJSONObject(i).getString("title")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Log.d("finally", "finally");
        }
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
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location != null){
                Log.d("latitude",location.getLatitude()+"");
                Log.d("longitude",location.getLongitude()+"");
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.equals(createMarker)){

            Bundle data = getIntent().getExtras();

            if(data != null){
                callTitle = data.getString("title");
                callDescription = data.getString("description");

                Log.d("title",callTitle);
                Log.d("description",callDescription);
            }

            Log.d("marker1",createMarker+"");
        }else{
            Log.d("marker1","no");
        }

        return false;
    }
}
