package com.example.ethantien.m4.controller;

import android.content.Intent;
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
                    if (validInput(latitude.getText().toString(), longitude.getText().toString())) {
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
     * returns true whether or not the values entered are valid
     * Valid = all textboxes are filled in, and the latitutde / longitutde values are valid
     * @param lat the latitude
     * @param longi the longitude
     * @return true of the values are valid, throws exception otherwise
     */
    private boolean validInput(String lat, String longi) {
        if (lat.equals("") || longi.equals("")) {
            throw new IllegalArgumentException("Please enter all information");
        } else {
            if (Double.parseDouble(lat) > 90 || Double.parseDouble(lat) < -90
                    || Double.parseDouble(longi) > 180 || Double.parseDouble(longi) < -180) {
                throw new IllegalArgumentException("Please enter valid coordinates");
            } else {
                return true;
            }
        }
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
}
