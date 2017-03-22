package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 3/12/17.
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
