package com.example.todolistphp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todolistphp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity {

    FusedLocationProviderClient providerClient;
    SupportMapFragment mapFragment;
    TextView tvGPS, tvLat, tvLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        providerClient = LocationServices.getFusedLocationProviderClient(this);
        Button btnTurnOn = (Button) findViewById(R.id.btnTurnOn);
        tvGPS = (TextView) findViewById(R.id.tvGPS);
        tvLat = (TextView) findViewById(R.id.tvLat);
        tvLong = (TextView) findViewById(R.id.tvLong);

        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // check gps permission
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        Boolean isGPSEnable = false;
        Context context = getApplicationContext();
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isGPSEnable == true){
            tvGPS.setText("Gps on");
        } else {
            tvGPS.setText("Gps off");
        }
    }


    private void getCurrentLocation(){
        Task<Location> task = providerClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //take value latlong current location
                            LatLng valueLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                            String messageLat = String.format(String.valueOf(location.getLatitude()));
                            String messageLong = String.format(String.valueOf(location.getLongitude()));
                            double latAwal, longAwal;
                            latAwal = location.getLatitude();
                            longAwal = location.getLongitude();

                            tvLat.setText("Lat  : "+ messageLat);
                            tvLong.setText("Long: "+ messageLong);

                            //make marker
                            MarkerOptions marker = new MarkerOptions().position(valueLatLong).title("You are in here "+location.getLatitude()+", "+ location.getLongitude());
                            googleMap.addMarker(marker);

                            //make new marker for destination
                            LatLng valueLatLong_other = new LatLng(-6.174292, 106.803268);
                            MarkerOptions marker_Gasstationother = new MarkerOptions().position(valueLatLong_other).title("lokasi tujuan"+ (-6.174292) + " " + 106.803268);
                            googleMap.addMarker(marker_Gasstationother);

                            //synchronization view map current location
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(valueLatLong, 15));

                            Location locationA = new Location("point A");
                            locationA.setLatitude(latAwal);
                            locationA.setLongitude(longAwal);

                            Location locationB = new Location("point B");

                            locationB.setLatitude(-6.174292);
                            locationB.setLongitude(106.803268);

                            float distance = locationA.distanceTo(locationB);

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }

    }
}

