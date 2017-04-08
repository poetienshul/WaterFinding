package com.example.ethantien.m4.model;

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

    /**
     * returns true whether or not the values entered are valid
     * Valid = all textboxes are filled in, and the latitutde / longitutde values are valid
     * @param lat the latitude
     * @param longi the longitude
     * @return true of the values are valid, throws exception otherwise
     */
    public static boolean validInput(String lat, String longi) {
        if (lat.equals("") || longi.equals("")) {
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
