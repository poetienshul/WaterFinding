package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ethantien.m4.Model.Report;
import com.example.ethantien.m4.Model.vars;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.ethantien.m4.R;

import java.util.ArrayList;

public class showMap extends AppCompatActivity implements OnMapReadyCallback, OnMarkerClickListener {

    ArrayList<Report> reports;
    ArrayList<MarkerOptions> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        reports = vars.getInstance().getReportList();
        markers = new ArrayList<>();
        for (Report ele : reports) {
            markers.add(new MarkerOptions()
                    .position(new LatLng(ele.getLocationLat(), ele.getLocationLong())).title(Integer.toString(ele.getReportNumber())));
        }


    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        map.setOnMarkerClickListener(this);
        for (MarkerOptions element : markers) {
            map.addMarker(element);
        }
        if (vars.getInstance().getCurrReport() != null) {

            LatLng latlng = new LatLng(vars.getInstance().getCurrReport().getLocationLat(),
                    vars.getInstance().getCurrReport().getLocationLong());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Report cur = null;
        for (Object r : vars.getInstance().getReportList()) {
            if (marker.getTitle().equals(Integer.toString(((Report)r).getReportNumber()))) {
                cur = (Report) r;
            }
        }
        vars.getInstance().setCurrReport(cur);
        startActivity(new Intent(showMap.this, viewReportDetails.class));
        return true;
    }
}
