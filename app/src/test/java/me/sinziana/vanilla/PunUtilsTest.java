/*
 * MIT License
 *
 * Copyright (c) ${YEAR} ${USER}
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.sinziana.vanilla;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class PunUtilsTest {

    @Test
    public void daysSinceLaunch_current() throws Exception {
        long current = (Calendar.getInstance().getTimeInMillis()/1000);
        assertEquals("With no offset we should the getting a value of 0", 0, PunUtils.daysSinceLaunch(current));
    }

    @Test
    public void daysSinceLaunch_2days() throws Exception {
        long days2 = 2*60*60*24;
        long current = (Calendar.getInstance().getTimeInMillis()/1000);
        assertEquals("The returned calculated offset since the date of launch does not match the expected value",
                2, PunUtils.daysSinceLaunch(current - days2));
    }

    @Test
    public void daysSinceLaunch_2days_future() throws Exception {
        long days2 = 2*60*60*24;
        long current = (Calendar.getInstance().getTimeInMillis()/1000);
        assertEquals("The method should return a negative number for days that are in the future",
                -2, PunUtils.daysSinceLaunch(current + days2));
    }

    @Test
    public void getDateWithOffset_current() throws Exception {
        assertEquals("With no offset we should be getting the current date",
                new Date(), PunUtils.getDateWithOffset(0));
    }

    @Test
    public void getDateWithOffset_4days() throws Exception {
        // We compare to second rather than milisecond because we might see differences because
        // of the time between the two timestamp calls.
        long timestamp = System.currentTimeMillis()/1000 + 4*60*60*24;
        long timestampToTest = (PunUtils.getDateWithOffset(4)).getTime()/1000;
        assertEquals("The timestamp from the date should match the timestamp with 4 days added",
                timestamp, timestampToTest);
    }

}