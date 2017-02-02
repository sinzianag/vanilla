package me.sinziana.vanilla;


import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class PunReader {

    public PunReader() {

    }

    public static HashSet<String> readPuns(String fileName, Activity activity) {
        HashSet<String> puns = new HashSet<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(activity.getAssets().open(fileName)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
               puns.add(mLine);
            }
        } catch (IOException e) {
            // TODO
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return puns;
    }
}
