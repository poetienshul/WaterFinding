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

import com.example.ethantien.m4.Model.Report;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class addReport extends AppCompatActivity {

    private EditText latitude;
    private EditText longitude;
    private Button add;
    private Button cancel;
    private Spinner choseType;
    private Spinner choseCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        add = (Button) findViewById(R.id.submitButton);
        cancel = (Button) findViewById(R.id.cancel);
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

                    //public Report (String date, String time, int reportNumber, String reporterName,
                    //Double locationLat, Double locationLong, String type, String condition) {
                    vars.getInstance().addReport(new Report(date, time, num, name, lat, longi,
                            waterType, waterCondition));


                    Toast.makeText(addReport.this, "New report created.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(addReport.this, WaterReports.class));
                    finish();

                }
            }
        });


    }
}
