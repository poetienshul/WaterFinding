package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 3/12/17.
 */

public class WaterPurityReport {
    private String date;
    private String time;
    private int reportNumber;
    private String reporterName;
    private Double locationLat;
    private Double locationLong;
    private String condition;
    private Double virusPPM;
    private Double contaminantPPM;

    public WaterPurityReport (String date, String time, int reportNumber, String reporterName,
                   Double locationLat, Double locationLong, String condition, Double virus, Double contaminant) {
        this.date = date;
        this.time = time;
        this.reportNumber = reportNumber;
        this.reporterName = reporterName;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.condition = condition;
        this.virusPPM = virus;
        this.contaminantPPM = contaminant;
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

    public Double getVirusPPM() {
        return virusPPM;
    }

    public Double getContaminantPPM() {
        return contaminantPPM;
    }
}
