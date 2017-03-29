package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewGraphSettings extends AppCompatActivity {

    TextView lat;
    TextView longi;
    RadioButton virus;
    RadioButton contaminant;
    TextView year;
    Button generate;
    Button cancel;

    boolean virusOn;
    boolean contaminantOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graph_settings);
        virusOn = false;
        contaminantOn = false;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewGraphSettings.this, "Database Error.", Toast.LENGTH_LONG).show();

            }
        });

        lat = (TextView) findViewById(R.id.enterLat);
        longi = (TextView) findViewById(R.id.enterLong);
        virus = (RadioButton) findViewById(R.id.virusButton);
        contaminant = (RadioButton) findViewById(R.id.contaminantButton);
        year = (TextView) findViewById(R.id.enterYear);
        generate = (Button) findViewById(R.id.generateGraph);
        cancel = (Button) findViewById(R.id.cancel);

        /**
         * button handler for the cancel button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewGraphSettings.this, startApplication.class));
                finish();
            }
        });

        /**
         * button handler for the generate Graph button
          */
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lat.getText().toString().equals("") || longi.getText().toString().equals("") ||
                    year.getText().toString().equals("") || !(virus.isChecked() ^ contaminant.isChecked())){
                    Toast.makeText(ViewGraphSettings.this, "Please enter all information.", Toast.LENGTH_LONG).show();
                } else {
                    Double latNum = Double.parseDouble(lat.getText().toString());
                    Double longNum = Double.parseDouble(longi.getText().toString());
                    int yearNum = Integer.parseInt(year.getText().toString());
                    String choice = "";
                    if (virus.isChecked()) {
                        choice = "Virus";
                    } else {
                        choice = "Contaminant";
                    }
                    if (latNum > 90 || latNum < -90 || longNum > 180 || longNum < -180) {
                        Toast.makeText(ViewGraphSettings.this, "Please enter valid coordinates.", Toast.LENGTH_LONG).show();
                    } else if (Integer.toString(yearNum).length() != 4){
                        Toast.makeText(ViewGraphSettings.this, "Please enter a valid year.", Toast.LENGTH_LONG).show();
                    } else {
                        vars.getInstance().setGraphLat(latNum);
                        vars.getInstance().setGraphLong(longNum);
                        vars.getInstance().setGraphYear(yearNum);
                        vars.getInstance().setGraphChoice(choice);
                        startActivity(new Intent(ViewGraphSettings.this, ViewGraph.class));
                        finish();
                    }
                }
            }

        });

    }

    public void onVirusButtonClicked(View view) {
        virus = (RadioButton) findViewById(R.id.virusButton);
        contaminant = (RadioButton) findViewById(R.id.contaminantButton);

        if (virusOn) {
            virus.setChecked(false);
        }
        if (contaminantOn){
            contaminant.setChecked(false);
            contaminantOn = false;
        }
        virusOn = !virusOn;

    }

    public void onContaminantButtonClicked(View view) {
        virus = (RadioButton) findViewById(R.id.virusButton);
        contaminant = (RadioButton) findViewById(R.id.contaminantButton);

        if (contaminantOn){
            contaminant.setChecked(false);
        }
        if (virusOn) {
            virus.setChecked(false);
            virusOn = false;

        }
        contaminantOn = !contaminantOn;
    }
}
