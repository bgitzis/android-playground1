package com.gitzis.android.playground.app.test.model;

import junit.framework.TestCase;
import android.text.format.Time;

/**
 * my take on simple junit in android.
 * for some reason runs in eclipse only through Run As..-> Android Junit Test
 * 
 * @author bgitzis
 */
public class SampleTest extends TestCase {
    public void testSampleTime() throws Exception {
        assertEquals("19700101T000000", new Time().format2445());
    }
}
