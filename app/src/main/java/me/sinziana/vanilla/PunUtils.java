/*
 * MIT License
 *
 * Copyright (c) 2017 Sinziana Gafitanu
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

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utilities class for the Puns app.
 * Simple methods that the app can use
 */

public class PunUtils {

    /**
     * The date format used in the application
     */
    private static final String DATE_FORMAT = "mm/dd/yyyy";

    private static final long launchTimestamp = 1487548800L; // March 1st, 2017

    /**
     * Check if the date we're passing is today
     * Date format must be "mm/dd/yyyy"
     *
     * @param dateString - String that we will check if it's today.
     * @return true if it's today
     */
    public static boolean isToday(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Date date = sdf.parse(dateString);

            return DateUtils.isToday(date.getTime());
        } catch (ParseException e) {
            Log.e("ParseException", "Could not get the date from the passed String: " + dateString);
        }

        return false;
    }

    public static int daysSinceLaunch() {
        long diff = (Calendar.getInstance().getTimeInMillis()/1000) - launchTimestamp;
        return Math.round(((diff/60)/60)/24);
    }

    /**
     * Get today's date in the format that we use to store the date through the application.
     *
     * @return string with today's date
     */
    public static String getTodayString() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    public static Date getDateWithOffset(int offset) {
        return new Date(Calendar.getInstance().getTimeInMillis() + offset*60*60*24*1000);
    }
}
