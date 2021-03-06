package com.example.sebastian.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Sebastian on 06/05/2017.
 */

public class TestLocation extends Service implements LocationListener {

    LocationManager locationManager;
    Context context;
    Location location;

    String method;

    boolean GPSEnabled;
    boolean networkEnabled;

    double latitude;
    double longitude;

    long deltadistance = 10;    // 10 mts
    long deltaTime = 1000 * 60; // 1 minuto

    public TestLocation(Context context) {
        this.context = context;
    }


    public String getMethod(){
            return method;
    }

    public Location getLocation() {

        location = null;
        try {

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            int permissionFineLoc = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

            Activity myparentActivity = (Activity) context;


            if (permissionFineLoc != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(myparentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }

            if(!networkEnabled)
                myparentActivity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (GPSEnabled || networkEnabled) {
                if (GPSEnabled) {
                    method = "GPS";
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, deltaTime, deltadistance, this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                    else{
                        location.setLatitude(0);
                        location.setLongitude(0);
                    }
                }
                else //network is enabled
                {
                    method = "NETWORK";
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, deltaTime, deltadistance, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                    else{
                        location.setLatitude(0);
                        location.setLongitude(0);
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
