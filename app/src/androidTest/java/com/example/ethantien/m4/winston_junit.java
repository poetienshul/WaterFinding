package com.example.ethantien.m4;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.ethantien.m4.model.WaterReport;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
/**Winston Li
 * Tests validInput() method in WaterReport.java
 * method used by addReport.java
 */

@RunWith(AndroidJUnit4.class)
public class winston_junit {

    @Test (expected = IllegalArgumentException.class)
    public void testAllEmptyText() {
        WaterReport.validInput("", "");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeEmpty() {
        WaterReport.validInput("", "50.0");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeEmpty() {
        WaterReport.validInput("50.0","");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeTooLow() {
        WaterReport.validInput("-91", "50");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeTooHigh() {
        WaterReport.validInput("91", "50");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeTooLow() {
        WaterReport.validInput("20", "-181");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeTooHigh() {
        WaterReport.validInput("20", "181");
    }

    @Test
    public void testValidInput() {
        assertTrue(WaterReport.validInput("33.78", "-84.39"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotNumbers() {
        assertTrue(WaterReport.validInput("Abc", "Abc"));
    }
}