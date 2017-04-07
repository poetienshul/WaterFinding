package com.example.ethantien.m4.model;

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



}
