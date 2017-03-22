package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ethantien.m4.Model.WaterPurityReport;
import com.example.ethantien.m4.Model.Worker;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

import java.util.ArrayList;

public class PurityReports extends AppCompatActivity {

    ArrayList<WaterPurityReport> listItems;

    ArrayAdapter<String> adapter;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_reports);

        final Button addReports = (Button) findViewById(R.id.addReport);
        Button back = (Button) findViewById(R.id.backList);
        listItems = vars.getInstance().getPurityList();

        //make listview of all water purity Reports
        ArrayList<String> titles = new ArrayList<>();
        for (WaterPurityReport ele : listItems) {
            titles.add(ele.getReportNumber() + ". <" + ele.getLocationLat() + ", " + ele.getLocationLong() + ">");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        list = (ListView) findViewById(R.id.lisp);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                vars.getInstance().setCurrPurityReport(vars.getInstance().getPurityAtIndex(position));
                startActivity(new Intent(PurityReports.this, viewPurityDetails.class));
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
                if (!(vars.getInstance().getCurrPerson() instanceof Worker)) {
                    Toast.makeText(PurityReports.this, "Only Workers may add Purity Reports", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(PurityReports.this, addPurityReport.class));
                }

            }
        });

        /**
         * Button handler for the back button
         * @param view the button
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PurityReports.this, startApplication.class));
                finish();
            }
        });
    }
}
