package com.example.ethantien.m4.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ethantien.m4.model.WaterPurityReport;
import com.example.ethantien.m4.model.Worker;
import com.example.ethantien.m4.model.vars;
import com.example.ethantien.m4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurityReports extends AppCompatActivity {

    private ArrayList<WaterPurityReport> listItems;

    private ArrayAdapter<String> adapter;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_reports);

        Button addReports = (Button) findViewById(R.id.addReport);
        Button back = (Button) findViewById(R.id.backList);
        Button refresh = (Button) findViewById(R.id.refreshButton);

        getReports();
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

        /**
         * Button handler for the refresh button
         * @param view the button
         */
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReports();
            }
        });
    }

    /**
     * go to the database, get a list of all the purity reports, and displays them on the screen
     */
    private void getReports() {
        listItems = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
        mDatabase.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listItems.add(snapshot.getValue(WaterPurityReport.class));
                }


                //make listview of all water purity Reports
                ArrayList<String> titles = new ArrayList<>();
                for (WaterPurityReport ele : listItems) {
                    titles.add(ele.getReportNumber() + ". <" + ele.getLocationLat() + ", " + ele.getLocationLong() + ">");
                }

                adapter = new ArrayAdapter<>(PurityReports.this, android.R.layout.simple_list_item_1, titles);
                list = (ListView) findViewById(R.id.lisp);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                        vars.getInstance().setCurrPurityReport(listItems.get(position));
                        startActivity(new Intent(PurityReports.this, viewPurityDetails.class));
                        finish();
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PurityReports.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}
