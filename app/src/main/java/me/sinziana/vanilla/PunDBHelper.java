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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.sinziana.vanilla.puns.AnimalPuns;
import me.sinziana.vanilla.puns.BatteryPuns;
import me.sinziana.vanilla.puns.ComputerPuns;
import me.sinziana.vanilla.puns.ElevatorPuns;
import me.sinziana.vanilla.puns.FoodPuns;
import me.sinziana.vanilla.puns.SpacePuns;

public class PunDBHelper {

    private static final String DATABASE_NAME = "pun_database";
    private static final int DATABASE_VERSION = 1;
    private static final String KEYWORDS = "keywords";
    private static final String PUNS = "puns";
    private static final String CAT_ID = "id";
    public static final String CAT_NAME = "name";

    public static final String PUN_TABLE = "FTS";
    public static final String CATEGORY_TABLE = "CATEGORY";

    private DatabaseOpenHelper _databaseHelper;

    public PunDBHelper(Context context) {
        System.out.println("_databaseHelper" + System.currentTimeMillis());
        _databaseHelper = new DatabaseOpenHelper(context);
        _databaseHelper.getReadableDatabase();
    }

    public Cursor query(String db_name, String[] columns, String selection, String[] selectionArgs, String groupBy,
                        String having, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(db_name);
        Cursor cursor = null;
        try {
            cursor = builder.query(_databaseHelper.getReadableDatabase(),
                    columns, selection, selectionArgs, groupBy, having, sortOrder);
        } catch (SQLiteException e) {
            Log.e("Database Error", "Bad Query" + e);
        }

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context _context;
        private SQLiteDatabase _database;

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + PUN_TABLE +
                        " USING fts3 (" +
                        KEYWORDS + ", " +
                        PUNS + ")";

        private static final String CATEGORY_TABLE_CREATE =
                "CREATE TABLE " + CATEGORY_TABLE +
                        " (" + CAT_ID + " INTEGER PRIMARY KEY," + CAT_NAME + " TEXT)";

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            System.out.println("DatabaseOpenHelper" + System.currentTimeMillis());
            _context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            _database = db;
            _database.execSQL(FTS_TABLE_CREATE);
            _database.execSQL(CATEGORY_TABLE_CREATE);
            System.out.println("onCreate" + System.currentTimeMillis());
            buildDatabase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DATABASE", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + PUN_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
            onCreate(db);
        }

        private void buildDatabase() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loadDatabase(AnimalPuns.FILE_NAME, PunCategory.ANIMAL);
                    loadDatabase(BatteryPuns.FILE_NAME, PunCategory.BATTERY);
                    loadDatabase(ComputerPuns.FILE_NAME, PunCategory.COMPUTER);
                    loadDatabase(ElevatorPuns.FILE_NAME, PunCategory.ELEVATOR);
                    loadDatabase(FoodPuns.FILE_NAME, PunCategory.FOOD);
                    loadDatabase(SpacePuns.FILE_NAME, PunCategory.SPACE);
                    loadCategory();
                }
            }).start();
        }

        private void loadCategory() {
            addCategory("animal");
            addCategory("battery");
            addCategory("computer");
            addCategory("elevator");
            addCategory("food");
            addCategory("space");
            addCategory("love");
            addCategory("occupation");
        }

        private void loadDatabase(String filename, String keyword) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(_context.getAssets().open(filename)));
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    addPuns(keyword, mLine);
                }
            } catch (IOException e) {
                Log.e("Database","IOException " + e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("Database","Could not close buffered reader " + e.getMessage());
                    }
                }
            }
        }

        private void addCategory(String category) {
            ContentValues row = new ContentValues();
            row.put(CAT_NAME, category);
            _database.insert(CATEGORY_TABLE, null, row);
        }

        private void addPuns(String keyword, String pun) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEYWORDS, keyword);
            initialValues.put(PUNS, pun);

            _database.insert(PUN_TABLE, null, initialValues);
        }
    }
}
