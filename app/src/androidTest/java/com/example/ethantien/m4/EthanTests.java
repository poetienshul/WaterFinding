package com.example.ethantien.m4;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
/**
 * Created by ethantien on 4/7/17.
 */

@RunWith(AndroidJUnit4.class)
public class EthanTests {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.ethantien.m4", appContext.getPackageName());
    }

}
