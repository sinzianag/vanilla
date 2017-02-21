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

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DbHelper extends SQLiteOpenHelper {

    // Constants
    private static final String DATABASE_NAME = "punDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "puns";
    public static final String FTS_TABLE_NAME = "fts_puns";

    private static final String PUN_COL = "pun";
    private static final String CATEGORY_COL = "category";
    public static final String DAY_COL = "punOfTheDay";
    private static final String FAVORITE_COL = "favorite";

    private int _dayOrder = 0;

    // private variables
    private SQLiteDatabase _database;
    private final Context _context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        _database = db;
        try {
            _database.execSQL("CREATE TABLE " + TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY, " +
                    PUN_COL + " TEXT," +
                    CATEGORY_COL + " TEXT," +
                    DAY_COL + " NUMBER," +
                    FAVORITE_COL + " BOOLEAN)");

            _database.execSQL("CREATE VIRTUAL TABLE " + FTS_TABLE_NAME +
                    " USING fts4 (content='" + TABLE_NAME + "', " + PUN_COL + "," + CATEGORY_COL + ")");
        }catch (SQLiteException e) {
            Log.e(LogConst.DATABASE, "Exception: " + e.getMessage());
            throw e;
        }

        populateDatabase();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(LogConst.DATABASE, "We're upgrading the database.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_NAME);
        onCreate(db);
    }

    private String buildFilename(String punCategory) {
        return punCategory + ".txt";
    }

    private void populateDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < PunCategory.categories.length; i++) {
                    loadDatabase(PunCategory.categories[i]);
                }
                updateFTSTable();
            }
        }).start();
    }

    private void updateFTSTable(){
        try {
            _database.execSQL("INSERT INTO " + FTS_TABLE_NAME + "(" + FTS_TABLE_NAME + ") VALUES('rebuild')");
        } catch (SQLiteException e) {
            Log.e(LogConst.DATABASE, "Exception: " + e.getMessage());
            throw e;
        }
    }

    private void loadDatabase(String punCategory) {
        String filename = buildFilename(punCategory);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(_context.getAssets().open(filename)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                addPuns(punCategory, mLine);
            }
        } catch (IOException e) {
            Log.e(LogConst.DATABASE,"IOException " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LogConst.DATABASE,"Could not close buffered reader " + e.getMessage());
                }
            }
        }
    }

    private void addPuns(String category, String pun) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CATEGORY_COL, category);
        initialValues.put(PUN_COL, pun);
        initialValues.put(DAY_COL, _dayOrder);
        initialValues.put(FAVORITE_COL, Boolean.FALSE);
        _dayOrder++;
        _database.insert(TABLE_NAME, null, initialValues);
    }


}
