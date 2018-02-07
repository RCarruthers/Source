package com.uom.ryan.potholes.application.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uom.ryan.potholes.application.location.LocationManager;
import com.uom.ryan.potholes.application.model.Report;

/**
 * Created by Ryan on 04/11/2017.
 */

public class SourcePresenterImpl implements SourcePresenter {

    private Context context;
    static LocationManager locationManager;
    static DatabaseReference database;

    public SourcePresenterImpl(Context context) {
        this.context = context;
        this.locationManager = LocationManager.getInstance(context);
        database = FirebaseDatabase.getInstance().getReference();

        context.registerReceiver(broadcastReceiver, new IntentFilter("BUTTON_PRESS_SINGLE"));
        context.registerReceiver(broadcastReceiver, new IntentFilter("BUTTON_PRESS_DOUBLE"));
        context.registerReceiver(broadcastReceiver, new IntentFilter("BUTTON_PRESS_HOLD"));
    }

    public void onMapReady(GoogleMap mMap) {
        LatLng home = new LatLng(53.4633086, -2.1839697);
        mMap.addMarker(new MarkerOptions().position(home).title("Home").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.setBuildingsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 17.0f));
        CameraPosition position = mMap.getCameraPosition();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(position.target)
                        .tilt(60)
                        .zoom(position.zoom)
                        .build()));
    }

    /*HACK FIX SOON*/
    public static void addReportToDatabase(Context context,  Report.Severity severity) {
        Log.d("test","test");
        Location lastLocation = locationManager.getLastLocation(context, severity);
    }

    public static void addReportToDatabase(final Context context, final Location lastLocation,  final Report.Severity severity) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String id = database.push().getKey();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid()).child("reportType");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Report report = new Report(lastLocation.getLatitude(), lastLocation.getLongitude(), dataSnapshot.getValue(String.class), severity);
                database.child("users").child(user.getUid()).child("reports").child(id).setValue(report);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Location lastLocation = locationManager.getLastLocation(intent.getAction());
            //addReportToDatabase();
        }
    };

}
