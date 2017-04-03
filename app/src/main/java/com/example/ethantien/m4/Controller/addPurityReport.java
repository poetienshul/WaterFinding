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

import com.example.ethantien.m4.model.WaterPurityReport;
import com.example.ethantien.m4.model.vars;
import com.example.ethantien.m4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addPurityReport extends AppCompatActivity {
    private EditText latitude;
    private EditText longitude;
    private Spinner choseCondition;
    private EditText virus;
    private EditText contaminant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purity_report);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button add = (Button) findViewById(R.id.submitButton);

        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);

        choseCondition = (Spinner) findViewById(R.id.conditionSpinner);
        virus = (EditText) findViewById(R.id.virusPPM);
        contaminant = (EditText) findViewById(R.id.contaminantPPM);


        /**
         * add types to the choseType spinner
         */
        ArrayList<String> types = new ArrayList<>();
        types.add("Safe");
        types.add("Treatable");
        types.add("Unsafe");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(addPurityReport.this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseCondition.setAdapter(adapter);

        /**
         * Button handler for the submit button
         * @param view the button
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude.getText().toString().equals("") || longitude.getText().toString().equals("")
                        || virus.getText().toString().equals("") || contaminant.getText().toString().equals("")) {
                    Toast.makeText(addPurityReport.this, "Please enter all information.", Toast.LENGTH_LONG).show();
                } else {
                    Double lat = Double.parseDouble(latitude.getText().toString());
                    Double longi = Double.parseDouble(longitude.getText().toString());
                    Double virusppm = Double.parseDouble(virus.getText().toString());
                    Double contaminantppm = Double.parseDouble(contaminant.getText().toString());
                    String waterCondition = choseCondition.getSelectedItem().toString();
                    if (lat > 90 || lat < -90 || longi > 180 || longi < -180) {
                        Toast.makeText(addPurityReport.this, "Please enter valid coordinates.", Toast.LENGTH_LONG).show();
                    } else {
                        addReport(lat, longi, waterCondition, virusppm, contaminantppm);

                        finish();
                    }
                }
            }
        });


        /**
         * Button handler for the cancel button
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addPurityReport.this, PurityReports.class));
            }
        });
    }

    /**
     * this method pulls the data that come from the textboxes that are on the screen, and then
     * pushes the new purity report to the Firebase Database if it is valid.
     * Valid = all textboxes are filled in, and the latitutde / longitutde values are valid
     * @param lat the latitutde
     * @param longi the longitude
     * @param condition the condition of the water
     * @param virus the VirusPPm of the water
     * @param contaminant the ContaminantPPM of the water
     */
    private void addReport(final Double lat, final Double longi, final String condition,
                                 final Double virus, final Double contaminant) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
        mDatabase.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lastCount = 1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    lastCount = snapshot.getValue(WaterPurityReport.class).getReportNumber() + 1;
                }
                String date = new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(new Date());
                String time = new SimpleDateFormat("hh:mm aaa", Locale.US).format(new Date());
                String name = vars.getInstance().getCurrPerson().getName();
                WaterPurityReport meow = new WaterPurityReport(date, time, lastCount, name, lat, longi,
                        condition, virus, contaminant);
                Map<String, Object> childUpdates = new HashMap<>();

                childUpdates.put("" + meow.getReportNumber(), meow);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
                mDatabase.updateChildren(childUpdates);
                Toast.makeText(addPurityReport.this, "New Purity Report created.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(addPurityReport.this, PurityReports.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addPurityReport.this, "Database Error", Toast.LENGTH_LONG).show();
            }


        });
    }
}
