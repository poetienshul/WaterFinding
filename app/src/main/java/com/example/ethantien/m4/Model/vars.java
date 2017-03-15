package com.example.ethantien.m4.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ethantien on 2/20/17.
 *
 * A class that holds information that needs to be accessed from any page in the application
 * Contains: HashMap for <Username, Person> and Person that holds the current logged in user.
 * an example of artifact of the singleton
 */

public class vars {

    private static final vars _instance = new vars();
    public static vars getInstance() { return _instance; }

    private HashMap<String, Person> userPass;
    private Person currPerson;

    private ArrayList<Report> waterReports;
    private ArrayList<WaterPurityReport> waterPurityReports;

    private vars() {
        userPass  = new HashMap<>();
        waterReports = new ArrayList<>(20);
        waterPurityReports = new ArrayList<>(20);
    }

    public void addPerson(String str, Person p) {
        userPass.put(str, p);
    }

    public void addReport(Report r) {
        waterReports.add(r);
    }

    public void addPurityReport(WaterPurityReport r) {
        waterPurityReports.add(r);
    }

    public ArrayList getReportList() {
        return waterReports;
    }

    public ArrayList getPurityList() {
        return waterPurityReports;
    }

    public Report getReportAtIndex(int index) {
        return waterReports.get(index);
    }

    public WaterPurityReport getPurityAtIndex(int index) {
        return waterPurityReports.get(index);
    }

    private Report currReport;
    private WaterPurityReport currPurityReport;

    public void setCurrReport(Report r) {
        currReport = r;
    }

    public Report getCurrReport() {
        return currReport;
    }

    public WaterPurityReport getCurrPurityReport() {
        return currPurityReport;
    }
    public void setCurrPurityReport(WaterPurityReport r) {
        currPurityReport = r;
    }

    public HashMap getMap() {
        return userPass;
    }

    public void setCurrPerson(Person p) {
        currPerson = p;
    }

    public Person getCurrPerson() {
        return currPerson;
    }

}
