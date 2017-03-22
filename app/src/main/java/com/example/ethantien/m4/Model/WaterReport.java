package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 2/28/17.
 * A generic report type.
 */

public class WaterReport extends Report{

    private String type;

    public WaterReport() {

    }

    public WaterReport(String date, String time, int reportNumber, String reporterName,
                       Double locationLat, Double locationLong, String type, String condition) {
        super(date, time, reportNumber, reporterName, locationLat, locationLong, condition);
        this.type = type;
    }


    public String getType() {
        return type;
    }
}
