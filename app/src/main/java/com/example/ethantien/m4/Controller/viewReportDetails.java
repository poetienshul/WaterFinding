package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ethantien.m4.Model.Report;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

public class viewReportDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_details);

        Report ele = vars.getInstance().getCurrReport();
        TextView title = (TextView) findViewById(R.id.textView13);
        title.setText("Water Report #" + vars.getInstance().getCurrReport().getReportNumber() + " Information");

        TextView location = (TextView) findViewById(R.id.location);
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        TextView type = (TextView) findViewById(R.id.type);
        TextView condition = (TextView) findViewById(R.id.condition);
        TextView name = (TextView) findViewById(R.id.name);

        location.setText(("<" + ele.getLocationLat() + ", " + ele.getLocationLong() + ">"));
        dateAndTime.setText(ele.getDate() + ", " + ele.getTime());
        type.setText(ele.getType());
        condition.setText(ele.getCondition());
        name.setText(ele.getReporterName());

        Button back = (Button) findViewById(R.id.back);

        /**
         * Button handler for the back button
         * @param view the button
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewReportDetails.this, WaterReports.class));
            }
        });


    }
}
