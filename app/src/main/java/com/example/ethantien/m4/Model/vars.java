package com.example.ethantien.m4.model;

import android.util.SparseArray;

import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by ethantien on 2/20/17.
 *
 * A class that holds information that needs to be accessed from any page in the application
 * Contains: variables for variables that need to interact with the graph.
 * an example of artifact of the singleton
 */

public class vars {




    private static final vars _instance = new vars();
    public static vars getInstance() { return _instance; }

    private vars() {
    }

    private Person currPerson;
    private WaterReport currWaterReport;
    private WaterPurityReport currPurityReport;

    private Double graphLat;
    private Double graphLong;
    private String graphChoice;
    private int graphYear;

    public Double getGraphLat() {
        return graphLat;
    }
    public Double getGraphLong() {
        return graphLong;
    }
    public String getGraphChoice() {
        return graphChoice;
    }
    public int getGraphYear() {
        return graphYear;
    }
    public void setGraphLat(Double dou) {
        graphLat = dou;
    }
    public void setGraphLong(Double dou) {
        graphLong = dou;
    }
    public void setGraphChoice(String str) {
        graphChoice = str;
    }
    public void setGraphYear(int num) {
        graphYear = num;
    }

    public WaterReport getCurrWaterReport() {
        return currWaterReport;
    }

    public void setCurrWaterReport(WaterReport r) {
        currWaterReport = r;
    }

    public WaterPurityReport getCurrPurityReport() {
        return currPurityReport;
    }
    public void setCurrPurityReport(WaterPurityReport r) {
        currPurityReport = r;
    }

    public Person getCurrPerson() {
        return currPerson;
    }

    public void setCurrPerson(Person p) {
        currPerson = p;
    }

    /**
     * returns true whether or not the input is valid
     * valid = no empty parameters, longitude/ latitude are valid, year is valid
     * @param lat latitude
     * @param longi longitude
     * @param year year
     * @param virus virusPPM
     * @param contaminant contaminant PPM
     * @return true of valid is input, throws exception otherwise
     */
    public static boolean validInput(String lat, String longi, String year, Boolean virus, Boolean contaminant) {
        if (lat.equals("") || longi.equals("") || year.equals("") || (virus == contaminant)) {
            throw new IllegalArgumentException("Please enter all information");
        } else {
            if (Double.parseDouble(lat) > 90 || Double.parseDouble(lat) < -90
                    || Double.parseDouble(longi) > 180 || Double.parseDouble(longi) < -180) {
                throw new IllegalArgumentException("Please enter valid coordinates");
            } else if (year.length() != 4) {
                throw new IllegalArgumentException("Please enter a valid year");
            } else {
                return true;
            }
        }
    }

    /**
     * helper method for the ViewGraph class that returns a data set of coordinates to be graphed
     * on a graph. Numbers in the same month will be averaged
     * @param arr SparseArray of months and Nodes to be plotted.
     * @return a DataPoint array of all the points after calculations
     */
    public static DataPoint[] getPoints(SparseArray<Node> arr) {
        DataPoint[] pts = new DataPoint[arr.size()];
        int counter = 0;
        for (int i = 0; i < arr.size(); i++) {
            int num = arr.keyAt(i);
            pts[counter++] = new DataPoint(num, arr.get(num).getValue() / arr.get(num).getCount());
        }
        return pts;
    }



}
