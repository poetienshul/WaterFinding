package com.example.ethantien.m4;

import android.support.test.runner.AndroidJUnit4;

import com.example.ethantien.m4.model.vars;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
/**
 * Created by ethantien on 4/7/17.
 * Tests every single condition of the validInput() input in vars.java, which ViewGraphSettings.java
 * requires to reference
 */

@RunWith(AndroidJUnit4.class)
public class EthanTests {

    @Test (expected = IllegalArgumentException.class)
    public void testAllEmptyText() {
        vars.validInput("", "", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeEmpty() {
        vars.validInput("", "12.2", "2016", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeEmpty() {
        vars.validInput("13.2", "", "2016", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testYearEmpty() {
        vars.validInput("12.3", "12.1", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testYearInvalid() {
        vars.validInput("12.3", "12.1", "12", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeTooLow() {
        vars.validInput("-112.3", "12.1", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeTooHigh() {
        vars.validInput("91", "12.1", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeTooLow() {
        vars.validInput("20", "-200.0", "2016", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeTooHigh() {
       vars.validInput("20", "200.0", "2016", true, false);
    }

    @Test
    public void testValidInput() {
        assertTrue(vars.validInput("33.78", "-84.39", "2016", true, false));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotNumbers() {
        vars.validInput("Abc", "Abc", "Abc", true, false);
    }
}
