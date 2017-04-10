package com.example.ethantien.m4;

import com.example.ethantien.m4.model.WaterPurityReport;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Qian on 4/9/2017.
 * Tests every single condition of the validInput() input in WaterPurityReport.java, which addPurityReport.java
 * requires to reference
 */
public class QianTests {

    //public static boolean validInput(String lat, String longi,  String virus, String contaminant) {
    //lat must be <+-90, long must be <+-180
    @Test (expected = IllegalArgumentException.class)
    public void testAllEmptyText() {
        WaterPurityReport.validInput("", "", "", "");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeEmpty() {
        WaterPurityReport.validInput("", "10", "10", "10");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeEmpty() {
        WaterPurityReport.validInput("50", "", "10", "10");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testVirusEmpty() {
        WaterPurityReport.validInput("50", "10", "", "10");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testContaminantEmpty() {
        WaterPurityReport.validInput("50", "10", "10", "");
    }

    @Test
    public void testLatitutdeValid() {
        assertTrue(WaterPurityReport.validInput("30", "10", "10", "10"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeInvalid() {
        WaterPurityReport.validInput("80", "200", "150", "150");
    }

    @Test
    public void testValidInput() {
        assertTrue(WaterPurityReport.validInput("80", "10", "10", "10"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotNumbers() {
        assertTrue(WaterPurityReport.validInput("Abc", "Abc", "Abc", "Abc"));
    }
}