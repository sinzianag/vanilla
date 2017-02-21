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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DbPunStorage {

    private SQLiteDatabase _db;

    public DbPunStorage(Context context) {
        Log.i(LogConst.PERFORMANCE, "Initialize the database" + System.currentTimeMillis());

        // Build the database as soon as we can
        DbHelper database = new DbHelper(context);
        _db = database.getReadableDatabase();
    }

    public Cursor searchForPuns(String query) {
        String[] selectionArgs = new String[] {query};
        return _db.rawQuery("SELECT * FROM " + DbHelper.FTS_TABLE_NAME +" WHERE " + DbHelper.FTS_TABLE_NAME + " MATCH ?", selectionArgs);
    }

    public String getRandomPun() {
        Cursor cur = _db.rawQuery("SELECT pun FROM "+ DbHelper.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1", null);
        if (cur != null) {
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                String randomPun = cur.getString(0);
                cur.close();
                return randomPun;
            }
        }

        return null;
    }

    public String getPunOfTheDay() {
        Cursor cur = _db.rawQuery("SELECT pun FROM "+ DbHelper.TABLE_NAME + " WHERE " + DbHelper.DAY_COL + "=" + PunUtils.daysSinceLaunch(), null);
        if (cur != null) {
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                String potd = cur.getString(0);
                cur.close();
                return potd;
            }
        }

        return null;
    }

    public Cursor query(String db_name, String[] columns, String selection, String[] selectionArgs, String groupBy,
                        String having, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(db_name);
        Cursor cursor = null;
        try {
            cursor = builder.query(_db, columns, selection, selectionArgs, groupBy, having, sortOrder);
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


}
