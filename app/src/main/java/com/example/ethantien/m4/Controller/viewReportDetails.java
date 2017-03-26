package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ethantien.m4.Model.WaterReport;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

/**
 * gets data for the current report and displays it to the user.
 */
public class viewReportDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_details);

        WaterReport ele = vars.getInstance().getCurrWaterReport();

        //set title
        TextView title = (TextView) findViewById(R.id.textView13);
        title.setText("Water Report #" + vars.getInstance().getCurrWaterReport().getReportNumber() + " Information");

        TextView location = (TextView) findViewById(R.id.location);
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        TextView type = (TextView) findViewById(R.id.type);
        TextView condition = (TextView) findViewById(R.id.condition);
        TextView name = (TextView) findViewById(R.id.name);


        //fetch data from information handlers
        location.setText(("<" + ele.getLocationLat() + ", " + ele.getLocationLong() + ">"));
        dateAndTime.setText(ele.getDate() + ", " + ele.getTime());
        type.setText(ele.getType());
        condition.setText(ele.getCondition());
        name.setText(ele.getReporterName());

        Button backList = (Button) findViewById(R.id.backList);
        Button backMap = (Button) findViewById(R.id.backMap);

        /**
         * Button handler for the back List button
         * @param view the button
         */
        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewReportDetails.this, WaterReports.class));
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
                startActivity(new Intent(viewReportDetails.this, showMap.class));
            }
        });

    }
}
