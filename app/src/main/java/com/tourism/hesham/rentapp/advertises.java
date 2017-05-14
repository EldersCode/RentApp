package com.tourism.hesham.rentapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class advertises extends AppCompatActivity {
private ImageView flats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertises);
flats=(ImageView) findViewById(R.id.flats);
        flats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LayoutInflater inflater = (this).getLayoutInflater();
                View view = LayoutInflater.from(advertises.this).inflate(R.layout.flats,null);
              AlertDialog.Builder builder = new AlertDialog.Builder(advertises.this);
                builder.setView(view);

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
                ////////////////
            }});

    }

    @Override
    public void onBackPressed() {
startActivity(new Intent(getApplicationContext(),MapsActivity.class));

    }
}
