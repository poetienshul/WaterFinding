package com.example.ethantien.m4.model;

/**
 * Created by ethantien on 3/12/17.
 * attributes and constructor for the water purity report
 */

public class WaterPurityReport extends Report{
    private Double virusPPM;
    private Double contaminantPPM;

    public WaterPurityReport() {

    }

    public WaterPurityReport (String date, String time, int reportNumber, String reporterName,
                   Double locationLat, Double locationLong, String condition, Double virus, Double contaminant) {
        super(date, time, reportNumber, reporterName, locationLat, locationLong, condition);
        this.virusPPM = virus;
        this.contaminantPPM = contaminant;
    }

    public Double getVirusPPM() {
        return virusPPM;
    }

    public Double getContaminantPPM() {
        return contaminantPPM;
    }
}
