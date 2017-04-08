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

    /**
     * Returns true if the values entered are valid.
     * Valid = all textboxes are filled in, and the latitutde / longitutde values are valid.
     * @param lat latitude
     * @param longi longitude
     * @param virus virusPPM of the water
     * @param contaminant contaminantPPM of the water.
     * @return true if the values are valid, throws exception otherwise
     */
    public static boolean validInput(String lat, String longi,  String virus, String contaminant) {
        if (lat.equals("") || longi.equals("") || virus.equals("") || contaminant.equals("")) {
            throw new IllegalArgumentException("Please enter all information");
        } else {
            if (Double.parseDouble(lat) > 90 || Double.parseDouble(lat) < -90
                    || Double.parseDouble(longi) > 180 || Double.parseDouble(longi) < -180) {
                throw new IllegalArgumentException("Please enter valid coordinates");
            } else {
                return true;
            }
        }
    }
}
