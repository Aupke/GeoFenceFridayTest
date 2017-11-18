package com.example.aupke.geofencetestfriday;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    private ArrayList<String> history;
    private String tag = "ErrorHandling";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void addGeofence(View view){
        Intent intent = new Intent(StartActivity.this, AddGeofenceActivity.class);
        startActivity(intent);
    }



}
