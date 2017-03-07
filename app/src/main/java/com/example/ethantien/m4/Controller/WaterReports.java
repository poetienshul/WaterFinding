package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ethantien.m4.Model.Report;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

import java.util.ArrayList;

/**
 * allows the user to view all the current water reports in the system.
 */
public class WaterReports extends AppCompatActivity {

    ArrayList<Report> listItems;

    ArrayAdapter<String> adapter;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reports);

        Button addReports = (Button) findViewById(R.id.addReport);
        Button back = (Button) findViewById(R.id.backList);
        listItems = vars.getInstance().getReportList();

        //make listview of all water reports
        ArrayList<String> titles = new ArrayList<>();
        for (Report ele : listItems) {
            titles.add(ele.getReportNumber() + ". <" + ele.getLocationLat() + ", " + ele.getLocationLong() + ">");
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,titles);
        list=(ListView) findViewById(R.id.lisp);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                vars.getInstance().setCurrReport(vars.getInstance().getReportAtIndex(position));
                startActivity(new Intent(WaterReports.this, viewReportDetails.class));
                finish();
            }
        });

        /**
         * Button handler for the Add Reports button
         * @param view the button
         */
        addReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WaterReports.this, addReport.class));
                finish();
            }
        });

        /**
         * Button handler for the back button
         * @param view the button
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WaterReports.this, startApplication.class));
                finish();
            }
        });




    }
}
