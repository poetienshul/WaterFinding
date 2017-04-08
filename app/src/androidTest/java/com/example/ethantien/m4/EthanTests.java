package com.example.ethantien.m4;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.ethantien.m4.controller.ViewGraphSettings;
import com.example.ethantien.m4.model.vars;

import org.junit.Before;
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

    @Before
    public void setUp() {
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.ethantien.m4", appContext.getPackageName());
    }

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
        vars.validInput("12.3", "12.1", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeTooLow() {
        vars.validInput("12.3", "12.1", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLatitutdeTooHigh() {
        vars.validInput("-91", "12.1", "", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeTooLow() {
        assertTrue(vars.validInput("20", "-200.0", "2016", true, false));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongitudeTooHigh() {
        assertTrue(vars.validInput("20", "200.0", "2016", true, false));
    }

    @Test
    public void testValidInput() {
        assertTrue(vars.validInput("33.78", "-84.39", "2016", true, false));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotNumbers() {
        assertTrue(vars.validInput("Abc", "Abc", "Abc", true, false));
    }
}
