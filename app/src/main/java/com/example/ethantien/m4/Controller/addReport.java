package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class addReport extends AppCompatActivity {

    private EditText latitude;
    private EditText longitude;
    private Button add;
    private Button cancel;
    private Spinner choseType;
    private Spinner choseCondition;


    private int lastCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        cancel = (Button) findViewById(R.id.cancel);
        addReport();
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
     * this method pulls the data that come from the textboxes that are on the screen, and then
     * pushes the new water report to the Firebase Database if it is valid.
     * Valid = all textboxes are filled in, and the latitutde / longitutde values are valid
     */
    private void addReport() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterReports");
        mDatabase.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    lastCount = snapshot.getValue(WaterReport.class).getReportNumber() + 1;
                }

                latitude = (EditText) findViewById(R.id.latitude);
                longitude = (EditText) findViewById(R.id.longitude);
                add = (Button) findViewById(R.id.submitButton);
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
                ArrayAdapter<String> adapter = new ArrayAdapter(addReport.this, android.R.layout.simple_spinner_item, types);
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
                ArrayAdapter<String> adapter2 = new ArrayAdapter(addReport.this, android.R.layout.simple_spinner_item, conditions);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                choseCondition.setAdapter(adapter2);


                /**
                 * Button handler for the submit button
                 * @param view the button
                 */
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (latitude.getText().toString().equals("") || longitude.getText().toString().equals("")) {
                            Toast.makeText(addReport.this, "Please enter all information.", Toast.LENGTH_LONG).show();
                        } else {
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
                                WaterReport meow = new WaterReport(date, time, lastCount, name, lat, longi, waterType, waterCondition);
                                //String key = mDatabase.child("WaterReports").push().getKey();
                                Map<String, Object> childUpdates = new HashMap<>();

                                childUpdates.put("" + meow.getReportNumber(), meow);
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterReports");
                                mDatabase.updateChildren(childUpdates);
                                Toast.makeText(addReport.this, "New report created.", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(addReport.this, WaterReports.class));

                                finish();
                                System.out.println(lastCount);
                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addReport.this, "Database Error", Toast.LENGTH_LONG).show();
            }


        });

    }
}
