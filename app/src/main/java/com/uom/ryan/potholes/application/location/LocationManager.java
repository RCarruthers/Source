package com.uom.ryan.potholes.application.location;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.uom.ryan.potholes.application.model.Report;
import com.uom.ryan.potholes.application.presenter.SourcePresenterImpl;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Created by Ryan on 04/11/2017.
 */

public class LocationManager {

    private Context context;
    private LocationRequest mLocationRequest;
    private Location lastLocation;
    private long MAX_UPDATE_INTERVAL = 200;  /* 5 secs */
    private long MIN_UPDATE_INTERVAL = 1; /* 2 sec */
    private static LocationManager INSTANCE = null;

    // singleton
    private LocationManager(Context c) {
        this.context = c;
    }

    public static LocationManager getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new LocationManager(context);
        return INSTANCE;
    }

    public void getUpdates() throws SecurityException {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(MAX_UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(MIN_UPDATE_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        //onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());

    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static Location getLastLocation(final Context context, final Report.Severity severity) throws SecurityException {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(context);
        final Location[] lastLocation = {null};
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //lastLocation[0] = location;
                           // Toast.makeText(context, "Success.", Toast.LENGTH_SHORT).show();
                            SourcePresenterImpl.addReportToDatabase(context, location, severity);

                        } else {
                            Toast.makeText(context, "Fail.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });

        return lastLocation[0];
    }

}
