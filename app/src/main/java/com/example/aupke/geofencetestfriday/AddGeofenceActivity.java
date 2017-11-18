package com.example.aupke.geofencetestfriday;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Locale;

public class AddGeofenceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "GeoFence";
    public static final int LOCATION_REQUEST_CODE = 1;
    public static final String geoCoderTAG = "geoCodeTAG";

    Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private int geoFenceRadius = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Google API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public LatLng fetchCoordinatesForLocation(String locationName) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            Address address = geocoder.getFromLocationName(locationName, 5).get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Log.e("Latlng", latLng.toString());
            return latLng;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getLocalizedMessage());
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public void translateLocation(View view) {
        Log.e("TEST", "TEST");
        TextView locationNameView = findViewById(R.id.locationInput);
        TextView titleNameView = findViewById(R.id.titleView);
        TextView contentView = findViewById(R.id.contentView);
        TextView rangeView = findViewById(R.id.rangeView);
        String locationNameText = locationNameView.getText().toString().trim();
        String contentText = contentView.getText().toString().trim();
        String textFromTitle = titleNameView.getText().toString().trim();
        int valueForRange = Integer.parseInt(rangeView.getText().toString().trim());

        LatLng latLng = fetchCoordinatesForLocation(locationNameText);

        Geofence geofence = new Geofence.Builder()
                .setRequestId(locationNameText)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setCircularRegion(latLng.latitude, latLng.longitude, valueForRange)
                .build();

        Log.e("LatLng: ", latLng.toString());
        GeofencingRequest request = new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
        Log.e("Range: ", valueForRange +"");
        Intent intent = new Intent(this, IntentHandle.class);
        intent.putExtra("TITLE_VIEW", textFromTitle);
        intent.putExtra("Content String", contentText);
        intent.putExtra("locationLat", latLng.latitude);
        intent.putExtra("locationLong", latLng.longitude);
        intent.putExtra("locationString", locationNameText);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (hasPermission() ==  false) ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_REQUEST_CODE);

        if (hasPermission()) {
            PendingResult<Status> p = LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, request, pendingIntent);
            Log.e("SUCCES", p.toString());
        }

        Intent backToHomeStartActivity = new Intent(AddGeofenceActivity.this, StartActivity.class);
        startActivity(backToHomeStartActivity);
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }



    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Google Play Services connected!");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Play Services connection suspended!");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Google Play Services connection failed!");
    }
}
