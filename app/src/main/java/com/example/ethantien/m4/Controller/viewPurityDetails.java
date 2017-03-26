package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ethantien.m4.Model.WaterPurityReport;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

public class viewPurityDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purity_details);

        WaterPurityReport ele = vars.getInstance().getCurrPurityReport();

        //set title
        TextView title = (TextView) findViewById(R.id.textView13);
        title.setText("Purity Report #" + vars.getInstance().getCurrPurityReport().getReportNumber() + " Information");

        TextView location = (TextView) findViewById(R.id.location);
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        TextView condition = (TextView) findViewById(R.id.condition);
        TextView name = (TextView) findViewById(R.id.name);
        TextView virus = (TextView) findViewById(R.id.virus);
        TextView contaminant = (TextView) findViewById(R.id.contaminant);


        //fetch data from information handlers
        location.setText(("<" + ele.getLocationLat() + ", " + ele.getLocationLong() + ">"));
        dateAndTime.setText(ele.getDate() + ", " + ele.getTime());
        condition.setText(ele.getCondition());
        name.setText(ele.getReporterName());
        virus.setText(ele.getVirusPPM().toString());
        contaminant.setText(ele.getContaminantPPM().toString());

        Button backList = (Button) findViewById(R.id.backList);
        Button backMap = (Button) findViewById(R.id.backMap);

        /**
         * Button handler for the back List button
         * @param view the button
         */
        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewPurityDetails.this, PurityReports.class));
                finish();
            }
        });

        /**
         * Button handler for the back Map button
         * @param view the button
         */
        backMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewPurityDetails.this, showMap.class));
            }
        });

    }
}
