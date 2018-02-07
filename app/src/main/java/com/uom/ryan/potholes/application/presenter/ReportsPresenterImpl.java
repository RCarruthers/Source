package com.uom.ryan.potholes.application.presenter;

import android.content.Context;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uom.ryan.potholes.R;
import com.uom.ryan.potholes.application.model.Report;
import com.uom.ryan.potholes.application.model.ReportViewHolder;
import com.uom.ryan.potholes.application.view.ReportsView;

/**
 * Created by Ryan on 04/11/2017.
 */

public class ReportsPresenterImpl implements ReportsPresenter {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ReportsView view;
    Context context;
    Query currentQuery;

    public ReportsPresenterImpl(Context context, ReportsView v) {
        this.context = context;
        this.view = v;
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentQuery = firebaseDatabase.getReference("users").child(user.getUid()).child("reports");
    }

    @Override
    public void populateAdapter() {
        final FirebaseRecyclerAdapter<Report, ReportViewHolder> adapter = new FirebaseRecyclerAdapter<Report, ReportViewHolder>(
                Report.class,
                R.layout.card,
                ReportViewHolder.class,
                currentQuery) {

            @Override
            protected void populateViewHolder(ReportViewHolder viewHolder, final Report model, final int position) {
                Log.d("populateViewHolder","Model is: " + model);
                viewHolder.setLat(model.getLat());
                viewHolder.setLon(model.getLon());
                viewHolder.setSeverity(model.getSeverity());

                MapView mapView = viewHolder.getMap();
                mapView.onCreate(null);
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        LatLng report = new LatLng(model.getLon(), model.getLat());
                        mMap.addMarker(new MarkerOptions().position(report).title("Your report").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(report, 16.0f));
                        CameraPosition position = mMap.getCameraPosition();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(position.target)
                                        .zoom(position.zoom)
                                        .build()));
                    }
                });
                viewHolder.setMap(mapView);
            }
        };

        view.setRecyclerViewAdapter(adapter);
    }

    @Override
    public void checkLoginStatus() {

    }


}
