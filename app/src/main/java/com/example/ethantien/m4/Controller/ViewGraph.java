package com.example.ethantien.m4.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ethantien.m4.model.vars;
import com.example.ethantien.m4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ViewGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graph);
        TextView title = (TextView) findViewById(R.id.title);
        String titleText = "Historical Report for " + vars.getInstance().getGraphYear();
        title.setText(titleText);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double lat = vars.getInstance().getGraphLat();
                Double longi = vars.getInstance().getGraphLong();
                String year = Integer.toString(vars.getInstance().getGraphYear());
                SparseArray<Node> elements = new SparseArray<>();

                for (DataSnapshot ele : dataSnapshot.getChildren()) {
                    String temp = ele.child("date").getValue().toString();
                    if (longi.equals(Double.parseDouble(ele.child("locationLong").getValue().toString()))
                        && lat.equals(Double.parseDouble(ele.child("locationLat").getValue().toString()))
                        && year.equals(temp.substring(temp.length() - 4))) {
                            int month = Integer.parseInt(ele.child("date").getValue().toString().substring(0, 2)) - 1;
                            Double virus = Double.parseDouble(ele.child("virusPPM").getValue().toString());
                            Double contam = Double.parseDouble(ele.child("contaminantPPM").getValue().toString());
                            if (vars.getInstance().getGraphChoice().equals("Virus")) {
                                if (elements.get(month) != null) {
                                    elements.get(month).addValue(virus);
                                    elements.get(month).incrementCount();
                                } else {
                                    elements.put(month, new Node(virus));
                                }

                            } else if (vars.getInstance().getGraphChoice().equals("Contaminant")){
                                if (elements.get(month) != null) {
                                    elements.get(month).addValue(contam);
                                    elements.get(month).incrementCount();
                                } else {
                                    elements.put(month, new Node(contam));
                                }
                            }
                    }
                }

                DataPoint[] pts = vars.getPoints(elements);

                GraphView graph = (GraphView) findViewById(R.id.graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pts);

                graph.addSeries(series);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(11);

                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
                        if (isValueX) {
                            // show normal x values
                            return months[(int)value];
                            //return super.formatLabel(value, isValueX);
                        } else {
                            // show currency for y values
                            return super.formatLabel(value, false);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewGraph.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
        Button cancel = (Button) findViewById(R.id.cancel);

        /**
         * Button handler for the cancel button
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewGraph.this, startApplication.class));
                finish();
            }
        });

    }


    /**
     * temp class just to be able to keep track of a month's value and the number of times it
     * shows up
     */
    public class Node {
        private Double value;
        private int count;

        Node(Double val) {
            value = val;
            count = 1;
        }
        public Double getValue() {
            return value;
        }
        public int getCount() {
            return count;
        }
        void addValue(Double num) {
            value += num;
        }
        void incrementCount() {
            count++;
        }
        public String toString() {
            return "Node: {Value:" + value + ", Count:" + count + "}";
        }
    }

}
