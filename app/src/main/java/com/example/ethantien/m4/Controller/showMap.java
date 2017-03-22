package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ethantien.m4.Model.WaterReport;
import com.example.ethantien.m4.Model.WaterPurityReport;
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

    ArrayList<WaterReport> waterReports;
    ArrayList<WaterPurityReport> purityReports;
    ArrayList<MarkerOptions> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        waterReports = vars.getInstance().getReportList();
        purityReports = vars.getInstance().getPurityList();
        markers = new ArrayList<>();
        for (WaterReport ele : waterReports) {
            markers.add(new MarkerOptions()
                    .position(new LatLng(ele.getLocationLat(), ele.getLocationLong())).title("WaterReport" + Integer.toString(ele.getReportNumber())));
        }
        for (WaterPurityReport reps : purityReports) {
            markers.add(new MarkerOptions()
                    .position(new LatLng(reps.getLocationLat(), reps.getLocationLong())).title("Purity" + Integer.toString(reps.getReportNumber())));
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
        if (vars.getInstance().getCurrWaterReport() != null) {

            LatLng latlng = new LatLng(vars.getInstance().getCurrWaterReport().getLocationLat(),
                    vars.getInstance().getCurrWaterReport().getLocationLong());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        WaterReport cur = null;
        WaterPurityReport curr = null;
        if (marker.getTitle().contains("WaterReport")) {
            for (Object r : vars.getInstance().getReportList()) {
                if (marker.getTitle().equals("WaterReport" + Integer.toString(((WaterReport)r).getReportNumber()))) {
                    cur = (WaterReport) r;
                }
            }
            vars.getInstance().setCurrWaterReport(cur);
            startActivity(new Intent(showMap.this, viewReportDetails.class));
        } else {
            for (Object r : vars.getInstance().getPurityList()) {
                if (marker.getTitle().equals("Purity" + Integer.toString(((WaterPurityReport)r).getReportNumber()))) {
                    curr = (WaterPurityReport) r;
                }
            }
            vars.getInstance().setCurrPurityReport(curr);
            startActivity(new Intent(showMap.this, viewPurityDetails.class));
        }

        return true;
    }
}
