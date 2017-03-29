package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ethantien.m4.Model.vars;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ViewGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graph);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Historical Report for " + vars.getInstance().getGraphYear());


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("WaterPurityReports");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double lat = vars.getInstance().getGraphLat();
                Double longi = vars.getInstance().getGraphLong();
                String year = Integer.toString(vars.getInstance().getGraphYear());
                //<index (month) ,value / count of elements>
                HashMap<Integer, Node> elements = new HashMap<>();
                //ArrayList<Node> elements = new ArrayList<>();
                for (DataSnapshot ele : dataSnapshot.getChildren()) {
                    String temp = ele.child("date").getValue().toString();
                    //System.out.println(Double.parseDouble(ele.child("locationLong").getValue().toString()));
                    if (longi.equals(Double.parseDouble(ele.child("locationLong").getValue().toString()))
                        && lat.equals(Double.parseDouble(ele.child("locationLat").getValue().toString()))
                        && year.equals(temp.substring(temp.length() - 4))) {
                            int month = Integer.parseInt(ele.child("date").getValue().toString().substring(0, 2)) - 1;
                            Double virus = Double.parseDouble(ele.child("virusPPM").getValue().toString());
                            Double contam = Double.parseDouble(ele.child("contaminantPPM").getValue().toString());
                            if (vars.getInstance().getGraphChoice().equals("Virus")) {
                                if (elements.containsKey(month)) {
                                    elements.get(month).addValue(virus);
                                    elements.get(month).incrementCount();
                                } else {
                                    elements.put(month, new Node(virus, 1));
                                }

                            } else if (vars.getInstance().getGraphChoice().equals("Contaminant")){
                                if (elements.containsKey(month)) {
                                    elements.get(month).addValue(contam);
                                    elements.get(month).incrementCount();
                                } else {
                                    elements.put(month, new Node(contam, 1));
                                }
                            }
                    }
                }

                DataPoint[] pts = new DataPoint[elements.size()];
                int counter = 0;
                //ArrayList<Node> = elements.keySet();
                for (Integer ele : elements.keySet()) {
                    System.out.println(ele + ", " + elements.get(ele).getValue() / elements.get(ele).getCount());
                    pts[counter++] = new DataPoint(ele, elements.get(ele).getValue() / elements.get(ele).getCount());
                }
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
                            return super.formatLabel(value, isValueX);
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
                startActivity(new Intent(ViewGraph.this, MainActivity.class));
                finish();
            }
        });

    }


    private class Node {
        private Double value;
        private int count;

        public Node(Double val, int num2) {
            value = val;
            count = num2;
        }
        public Double getValue() {
            return value;
        }
        public int getCount() {
            return count;
        }
        public void addValue(Double num) {
            value += num;
        }
        public void incrementCount() {
            count++;
        }
    }

}
