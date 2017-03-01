package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 2/28/17.
 */

public class Report {
    private String date;
    private String time;
    private int reportNumber;
    private String reporterName;
    private Double locationLat;
    private Double locationLong;
    private String type;
    private String condition;

    public Report (String date, String time, int reportNumber, String reporterName,
                   Double locationLat, Double locationLong, String type, String condition) {
        this.date = date;
        this.time = time;
        this.reportNumber = reportNumber;
        this.reporterName = reporterName;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public String getCondition() {
        return condition;
    }
}
