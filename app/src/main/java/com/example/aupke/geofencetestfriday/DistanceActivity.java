package com.example.aupke.geofencetestfriday;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Aupke on 17-11-2017.
 */

public class DistanceActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance);



        TextView distanceView = (TextView) findViewById(R.id.DistanceView);
        distanceView.setText("" + 33 + " meters to location");
    }
}
