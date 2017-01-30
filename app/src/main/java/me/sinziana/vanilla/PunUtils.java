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
 *
 * sinziana on 1/29/17.
 */

public class PunUtils {

    /**
     * The date format used in the application
     */
    public static final String DATE_FORMAT = "mm/dd/yyyy";

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

    /**
     * Get today's date in the format that we use to store the date throught the application.
     *
     * @return string with today's date
     */
    public static String getTodayString() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }
}
