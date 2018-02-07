package com.uom.ryan.potholes.application.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.uom.ryan.potholes.R;

/**
 * Created by Ryan on 04/11/2017.
 */

public class ReportViewHolder extends  RecyclerView.ViewHolder {

    TextView latTextView;

    TextView lonTextView;

    TextView severityTextView;

    MapView mapView;
    
    public ReportViewHolder(final View itemView) {
        super(itemView);
        latTextView = (TextView) itemView.findViewById(R.id.card_lat);
        lonTextView = (TextView) itemView.findViewById(R.id.card_lon);
        severityTextView = (TextView) itemView.findViewById(R.id.card_sev);
        mapView = (MapView) itemView.findViewById(R.id.card_map);

        final LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.descriptionLinearLayout);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility() == View.GONE)
                    layout.setVisibility(View.VISIBLE);
                else if(layout.getVisibility() == View.VISIBLE)
                    layout.setVisibility(View.GONE);
            }
        });
    }

    public void setLat(double lat) {
        latTextView.setText("Latitude: " + String.valueOf(lat));
    }

    public void setLon(double lon) { lonTextView.setText("Longitude: " + String.valueOf(lon)); }

    public void setSeverity(String severity) {
        if(severity != null)
            severityTextView.setText("Severity: " + severity.toUpperCase());
    }

    public MapView getMap() {
        return mapView;
    }

    public void setMap(MapView map) {
        mapView = map;
    }
}
