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

import com.example.ethantien.m4.Model.WaterPurityReport;
import com.example.ethantien.m4.Model.WaterReport;
import com.example.ethantien.m4.Model.vars;
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
import java.util.Map;

public class addPurityReport extends AppCompatActivity {
    private EditText latitude;
    private EditText longitude;
    private Button add;
    private Button cancel;
    private Spinner choseCondition;
    private EditText virus;
    private EditText contaminant;

    private int lastCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purity_report);
        cancel = (Button) findViewById(R.id.cancel);
        addPurityReport();



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

    private void addPurityReport() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
        mDatabase.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    lastCount = snapshot.getValue(WaterPurityReport.class).getReportNumber() + 1;
                }

                latitude = (EditText) findViewById(R.id.latitude);
                longitude = (EditText) findViewById(R.id.longitude);
                add = (Button) findViewById(R.id.submitButton);
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
                ArrayAdapter<String> adapter = new ArrayAdapter(addPurityReport.this, android.R.layout.simple_spinner_item, types);
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
                        }
                        Double lat = Double.parseDouble(latitude.getText().toString());
                        Double longi = Double.parseDouble(longitude.getText().toString());
                        Double virusppm = Double.parseDouble(virus.getText().toString());
                        Double contaminantppm = Double.parseDouble(contaminant.getText().toString());
                        if (lat > 90 || lat < -90 || longi > 180 || longi < -180) {
                            Toast.makeText(addPurityReport.this, "Please enter valid coordinates.", Toast.LENGTH_LONG).show();
                        } else {
                            String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
                            String time = new SimpleDateFormat("hh:mm aaa").format(new Date());
                            String waterCondition = choseCondition.getSelectedItem().toString();
                            String name = vars.getInstance().getCurrPerson().getName();

                            WaterPurityReport meow = new WaterPurityReport(date, time, lastCount, name, lat, longi,
                                    waterCondition, virusppm, contaminantppm);
                            //String key = mDatabase.child("WaterReports").push().getKey();
                            Map<String, Object> childUpdates = new HashMap<>();

                            childUpdates.put("" + meow.getReportNumber(), meow);
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
                            mDatabase.updateChildren(childUpdates);
                            Toast.makeText(addPurityReport.this, "New Purity Report created.", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(addPurityReport.this, PurityReports.class));

                            finish();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addPurityReport.this, "Database Error", Toast.LENGTH_LONG).show();
            }


        });
    }
}
