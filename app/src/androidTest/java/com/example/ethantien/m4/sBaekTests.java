package com.example.ethantien.m4;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.ethantien.m4.model.Person;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sam Baek on 4/9/2017.
 * Tests every single condition of the validInput() input in Person.java, which
 * Register.java requires to reference
 */

@RunWith(AndroidJUnit4.class)
public class sBaekTests {

    private final String emptyString = "   ";
    private final String validId = "sammybaek";
    private final String validPW = "SammyBaek1234";
    private final String validName = "Sammy Baek";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.ethantien.m4", appContext.getPackageName());
    }


    @Test ()
    public void testValidInput() {
        assertTrue(Person.validInput(validId, validPW, validName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIdEmpty() {
        Person.validInput(emptyString, validPW, validName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPwEmpty() {
        Person.validInput(validId, emptyString,validName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNameEmpty() {
        Person.validInput(validId, validPW, emptyString);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIdShort() {
        Person.validInput("12345", validPW, validName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPwShort() {
        Person.validInput(validId, "12345", validName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPwNoUpperCase() {
        Person.validInput(validId, "password1234", validName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPwNoLowerCase() {
        Person.validInput(validId, "PASSWORD1234", validName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPwNoDigit() {
        Person.validInput(validId, "Password", validName);
    }


}
