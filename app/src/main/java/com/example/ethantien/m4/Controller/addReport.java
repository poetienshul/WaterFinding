package com.example.ethantien.m4.controller;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.ethantien.m4.model.WaterReport;
import com.example.ethantien.m4.model.vars;
import com.example.ethantien.m4.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addReport extends AppCompatActivity {

    private EditText latitude;
    private EditText longitude;
    private Spinner choseType;
    private Spinner choseCondition;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button add = (Button) findViewById(R.id.submitButton);

        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        choseType = (Spinner) findViewById(R.id.typeSpinner);
        choseCondition = (Spinner) findViewById(R.id.conditionSpinner);
        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        /**
         * add types to the choseType spinner
         */
        ArrayList<String> types = new ArrayList<>();
        types.add("Bottled");
        types.add("Well");
        types.add("Stream");
        types.add("Lake");
        types.add("Spring");
        types.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(addReport.this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseType.setAdapter(adapter);

        /**
         * add types to the choseCondition spinner
         */
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("Waste");
        conditions.add("Treatable - Clear");
        conditions.add("Treatable - Muddy");
        conditions.add("Potable");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(addReport.this, android.R.layout.simple_spinner_item, conditions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseCondition.setAdapter(adapter2);


        /**
         * Button handler for the submit button
         * @param view the button
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (WaterReport.validInput(latitude.getText().toString(), longitude.getText().toString())) {
                        addWaterReport(Double.parseDouble(latitude.getText().toString()),
                                Double.parseDouble(longitude.getText().toString()),
                                choseType.getSelectedItem().toString(),
                                choseCondition.getSelectedItem().toString());
                        Toast.makeText(addReport.this, "New report created.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(addReport.this, WaterReports.class));
                        finish();
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(addReport.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        /**
         * Button handler for the cancel button
         *
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addReport.this, WaterReports.class));
            }
        });


    }



    /**
     * creates a new water report and then
     * pushes the new water report to the Firebase Database if it is valid.
     * Valid = all textboxes are filled in, and the latitutde / longitutde values are valid
     * @param lat the latitude
     * @param longi the longitude
     * @param waterType the type of the water
     * @param waterCondition the condition of the water
     */
    private void addWaterReport(final Double lat, final Double longi, final String waterType, final String waterCondition) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterReports");
        mDatabase.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lastCount = 1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    lastCount = snapshot.getValue(WaterReport.class).getReportNumber() + 1;
                }
                String date = new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(new Date());
                String time = new SimpleDateFormat("hh:mm aaa", Locale.US).format(new Date());

                String name = vars.getInstance().getCurrPerson().getName();
                WaterReport temp = new WaterReport(date, time, lastCount, name, lat, longi, waterType, waterCondition);
                Map<String, Object> childUpdates = new HashMap<>();

                childUpdates.put("" + temp.getReportNumber(), temp);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterReports");
                mDatabase.updateChildren(childUpdates);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addReport.this, "Database Error", Toast.LENGTH_LONG).show();
            }


        });

    }
    public void getLocation(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.getApplicationContext(), "Grant location access!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(addReport.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this.getApplicationContext(), "Turn on GPS!", Toast.LENGTH_SHORT).show();
            return;
        }
        Location loc = getLastKnownLocation();
        if (loc == null) {
            Toast.makeText(this.getApplicationContext(), "Cannot locate!", Toast.LENGTH_SHORT).show();
            return;
        }
        latitude.setText(loc.getLatitude() + "");
        longitude.setText(loc.getLongitude() + "");
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
