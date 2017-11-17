package com.example.aupke.geofencetestfriday;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Aupke on 17-11-2017.
 */

public class DistanceActivity extends AppCompatActivity implements LocationListener {

    private LocationManager mLocMgr;
    private TextView tv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FrameLayout rl = new FrameLayout(this.getApplicationContext());
        LinearLayout ll = new LinearLayout(this.getApplicationContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        setContentView(rl);
        rl.addView(ll);

        tv1 = new TextView(getApplicationContext());
        ll.addView(tv1);

        //setContentView(R.layout.main);
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, this);
    }


    @Override
    public void onLocationChanged(Location location) {

        double latitude = getIntent().getExtras().getDouble("locationLat");
        double longitude = getIntent().getExtras().getDouble("locationLong");


        Location abogade = new Location("");
        abogade.setLatitude(latitude);
        abogade.setLongitude(longitude);
        float locationMeters = location.distanceTo(abogade);
        tv1.setText("" +  locationMeters + " Meters");
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}

