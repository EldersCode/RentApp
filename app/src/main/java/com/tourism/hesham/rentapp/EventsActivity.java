package com.tourism.hesham.rentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        finish();
    }
}
