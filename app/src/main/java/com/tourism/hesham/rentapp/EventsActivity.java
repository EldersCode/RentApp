package com.tourism.hesham.rentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EventsActivity extends AppCompatActivity {

    private LinearLayout  TheEventTypesContainor;
    private ImageView ToggleTheEventTypeContainorArrow;
    private boolean IsItHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        TheEventTypesContainor = (LinearLayout) findViewById(R.id.EventTypeContanierid);
        TheEventTypesContainor.setVisibility(LinearLayout.GONE);

        IsItHidden =true;

        ToggleTheEventTypeContainorArrow=(ImageView)findViewById(R.id.ToggleTheEventTypeContainorArrowId);

        ToggleTheEventTypeContainorArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IsItHidden ==true) {
                    TheEventTypesContainor.setVisibility(LinearLayout.VISIBLE);
                    IsItHidden=false;
                }
                else{TheEventTypesContainor.setVisibility(LinearLayout.GONE);
                    IsItHidden=true;
                }


            }
        });


    }



}

