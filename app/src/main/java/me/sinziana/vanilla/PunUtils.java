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

import android.graphics.Color;

import java.util.Calendar;
import java.util.Date;

/**
 * Utilities class for the Puns app.
 * Simple methods that the app can use
 */

public class PunUtils {

    public static long SECONDS_IN_DAY = 60*60*24;
    public static long MILI_IN_SEC = 1000;

    public static int daysSinceLaunch(long launchTimestamp) {
        long diff = (Calendar.getInstance().getTimeInMillis()/MILI_IN_SEC) - launchTimestamp;
        return Math.round(diff/SECONDS_IN_DAY);
    }

    public static Date getDateWithOffset(int offset) {
        return new Date(Calendar.getInstance().getTimeInMillis() + offset * SECONDS_IN_DAY * MILI_IN_SEC);
    }

    public static int getGradientColor(int position) {
        int baseColor = Color.parseColor("#79cec5");
        float[] hsv = new float[3];
        Color.colorToHSV(baseColor, hsv);
        hsv[2] *= (1.0f - 0.1f * position);
        int color = Color.HSVToColor(hsv);
        return color;
    }
}
