package com.tourism.hesham.rentapp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import handling.recycler.RecyclerAdapter;
import handling.recycler.Recycler_Listener;

public class LocateOnMap extends FragmentActivity implements OnMapReadyCallback
     ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    protected GoogleMap mMap;
    GoogleApiClient googleApiClient ;
    Location mLastLocation;

    ////
    private static final LatLngBounds myBounds = new LatLngBounds(
            new LatLng(-0,0),new LatLng(0,0));

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;
    private EditText search_editText;
    private ImageView search_img;

    final static String fixedHttp = "https://maps.googleapis.com/maps/api/geocode/json?";
    final static String apiKey = "AIzaSyB9m1fot-VHreEUQxeMNQaF3RJ92VL6f_0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_on_map);
        buildGoogleApiClient();




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Initializing();
        AutocompleteApi();
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAddressAd();
            }
        });
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        //to animate camera on the last location when gps closed (it needs database)
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED  &&  mLastLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 12));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    private void Initializing(){
        search_editText = (EditText)findViewById(R.id.search_editTextAd);
        search_img = (ImageView)findViewById(R.id.search_imgAd);
        recyclerAdapter = new RecyclerAdapter(this, R.layout.recycler_row , googleApiClient, myBounds , null);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerIdAd);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    private void  AutocompleteApi(){

        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().equals("") && googleApiClient.isConnected()){
                    recyclerAdapter.getFilter().filter(s.toString());
                }
                else if(!googleApiClient.isConnected()){
                    Toast.makeText(getApplicationContext(), "Google API Client is not connected", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mRecyclerView.addOnItemTouchListener(

                new Recycler_Listener(this, new Recycler_Listener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view , int position) {
                        final RecyclerAdapter.AT_Place item = recyclerAdapter.getItem(position);
                        final String placeId = String.valueOf(item.placeId);

                        /*
                             Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                         */

                        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                                .getPlaceById(googleApiClient, placeId);
                        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if(places.getCount()==1){
                                    //Do the things here on Click.....
                                    Toast.makeText(getApplicationContext(), "please click on Search button on the left ..",Toast.LENGTH_SHORT).show();

                                    //LatLng latLng = String.valueOf(places.get(0).getLatLng()) ;

                                    //////////// hena bageb el latitude w el longitude w ab3thom lel map !! aw a7sn a5ally el edit text yeb2a feh el address
                                    search_editText.setText(String.valueOf(places.get(0).getAddress()));
                                    mRecyclerView.setVisibility(View.GONE);

                                }else {
                                    Toast.makeText(getApplicationContext(),"Something went wrong !",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Log.i("TAG", "Clicked: " + item.description);
                        Log.i("TAG", "Called getPlaceById to get Place details for " + item.placeId);
                    }
                })
        );


    }


    public void findAddressAd() {

        //hn5ally el keyboard te5tfy b3d ma el user y5allas ketaba

        InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethod.hideSoftInputFromWindow(search_editText.getWindowToken(),0);

        try {

            if (search_editText.length() == 0) {
                Log.i("Empty" , "EditText is Empty");
                Toast.makeText(this, "Please enter the address you want to search for ..", Toast.LENGTH_SHORT).show();
            } else {


                //hn5ally el search editText yeb2a feh + ben kol kelma fl address (n7welha le URL form)

                String encodedAddress = URLEncoder.encode(search_editText.getText().toString(), "UTF-8");
                String  httpWeb = fixedHttp + "address=" + encodedAddress + "&key=" + apiKey;

                Log.i("httpWeb", httpWeb);



                    // we open connection by the DownloadTask Class
                    DownloadTask task = new DownloadTask();
                    // we used the interface to get the data we have after the onPostExecute method finished to use it ..
                    task.execute(httpWeb);
                search_editText.getText().clear();





            }
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            Toast.makeText(this, "Please enter the address you want to search for ..", Toast.LENGTH_SHORT).show();
        }


    }




    private  class DownloadTask extends AsyncTask<String , Void , String> {

        Double latitude;
        Double longitude;




        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection connection;

            try {

                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);


                int data = reader.read();
                while (data != -1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);
                String results = jsonObject.getString("results");
                Log.i("results" , results);
                JSONArray arr = new JSONArray(results);

                for(int i=0 ; i<arr.length() ; i++){

                    JSONObject jsonPart = arr.getJSONObject(i);
                    JSONObject jsonGeometry = jsonPart.getJSONObject("geometry");
                    JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
                    final String lat = jsonLocation.getString("lat");
                    String lng = jsonLocation.getString("lng");

                    latitude = Double.valueOf(lat);
                    longitude = Double.valueOf(lng);
                    Log.i("lat & lng :" , latitude + "  " +longitude);


                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude , longitude)).title("here")
                            .icon(BitmapDescriptorFactory.defaultMarker()).draggable(true));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 7));
                    mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {
//                            LatLng latLng = marker.getPosition();
//                            mMap.addMarker(new MarkerOptions().position(latLng));
                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {
//                            LatLng latLng = marker.getPosition();
//                            mMap.addMarker(new MarkerOptions().position(latLng));
                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            //lma el marker yo2af f a5er makan howa da el latlng
                            LatLng latLng = marker.getPosition();
                            
                        }
                    });



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting() ){
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext() , MapsActivity.class));
    }
}
