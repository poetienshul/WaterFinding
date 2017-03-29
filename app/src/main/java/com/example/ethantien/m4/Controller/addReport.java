package com.example.ethantien.m4.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ethantien.m4.Model.WaterReport;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class addReport extends AppCompatActivity {

    private EditText latitude;
    private EditText longitude;
    private Button add;
    private Button cancel;
    private Button locationButton;
    private Spinner choseType;
    private Spinner choseCondition;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        add = (Button) findViewById(R.id.submitButton);
        cancel = (Button) findViewById(R.id.cancel);
        locationButton = (Button) findViewById(R.id.location_button);
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
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, types);
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
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, conditions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseCondition.setAdapter(adapter2);


        /**
         * Button handler for the cancel button
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addReport.this, WaterReports.class));
            }
        });

        /**
         * Button handler for the submit button
         * @param view the button
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude.getText().toString().equals("") || longitude.getText().toString().equals("")) {
                    Toast.makeText(addReport.this, "Please enter all information.", Toast.LENGTH_LONG).show();
                }
                Double lat = Double.parseDouble(latitude.getText().toString());
                Double longi = Double.parseDouble(longitude.getText().toString());
                if (lat > 90 || lat < -90 || longi > 180 || longi < -180) {
                    Toast.makeText(addReport.this, "Please enter valid coordinates.", Toast.LENGTH_LONG).show();
                } else {
                    String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
                    String time = new SimpleDateFormat("hh:mm aaa").format(new Date());
                    String waterType = choseType.getSelectedItem().toString();
                    String waterCondition = choseCondition.getSelectedItem().toString();
                    String name = vars.getInstance().getCurrPerson().getName();
                    int num = vars.getInstance().getReportList().size() + 1;

                    //public WaterReport (String date, String time, int reportNumber, String reporterName,
                    //Double locationLat, Double locationLong, String type, String condition) {
                    vars.getInstance().addReport(new WaterReport(date, time, num, name, lat, longi,
                            waterType, waterCondition));


                    Toast.makeText(addReport.this, "New report created.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(addReport.this, WaterReports.class));
                    finish();

                }
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
