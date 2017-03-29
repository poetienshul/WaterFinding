package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showMap extends AppCompatActivity implements OnMapReadyCallback, OnMarkerClickListener {

    ArrayList<WaterReport> waterReports;
    ArrayList<WaterPurityReport> purityReports;
    ArrayList<MarkerOptions> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        waterReports = new ArrayList<>();
        purityReports = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot temp = dataSnapshot.child("WaterReports");
                for (DataSnapshot snapshot : temp.getChildren()) {
                    waterReports.add(snapshot.getValue(WaterReport.class));
                }

                DataSnapshot temp2 = dataSnapshot.child("WaterPurityReports");
                for (DataSnapshot snapshot : temp2.getChildren()) {
                    purityReports.add(snapshot.getValue(WaterPurityReport.class));
                }
                markers = new ArrayList<>();
                for (WaterReport ele : waterReports) {
                    markers.add(new MarkerOptions()
                            .position(new LatLng(ele.getLocationLat(), ele.getLocationLong())).title("WaterReport" + Integer.toString(ele.getReportNumber())));
                }
                for (WaterPurityReport reps : purityReports) {
                    markers.add(new MarkerOptions()
                            .position(new LatLng(reps.getLocationLat(), reps.getLocationLong())).title("Purity" + Integer.toString(reps.getReportNumber())));
                }
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(showMap.this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(showMap.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });

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
        } else if (vars.getInstance().getCurrPurityReport() != null) {
            LatLng latlng = new LatLng(vars.getInstance().getCurrPurityReport().getLocationLat(),
                    vars.getInstance().getCurrPurityReport().getLocationLong());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        WaterReport cur = null;
        WaterPurityReport curr = null;
        if (marker.getTitle().contains("WaterReport")) {
            for (Object r : waterReports) {
                if (marker.getTitle().equals("WaterReport" + Integer.toString(((WaterReport)r).getReportNumber()))) {
                    cur = (WaterReport) r;
                }
            }
            vars.getInstance().setCurrWaterReport(cur);
            startActivity(new Intent(showMap.this, viewReportDetails.class));
        } else {
            for (Object r : purityReports) {
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
