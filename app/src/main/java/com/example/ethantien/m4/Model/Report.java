package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 3/20/17.
 */

public abstract class Report {

    private String date;
    private String time;
    private int reportNumber;
    private String reporterName;
    private Double locationLat;
    private Double locationLong;
    private String condition;

    public Report() {

    }

    public Report (String date, String time, int reportNumber, String reporterName,
                              Double locationLat, Double locationLong, String condition) {
        this.date = date;
        this.time = time;
        this.reportNumber = reportNumber;
        this.reporterName = reporterName;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public String getReporterName() {
        return reporterName;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public String getCondition() {
        return condition;
    }


}
