package me.sinziana.vanilla;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import me.sinziana.vanilla.puns.AnimalPuns;

/**
 * sinziana on 1/21/17.
 */

public class PunDatabase {

    private static final String DATABASE_NAME = "pun_database";
    private static final int DATABASE_VERSION = 1;
    private static final String KEYWORDS = "keywords";
    private static final String PUNS = "pun_list";

    private static final String FTS_VIRTUAL_TABLE = "FTS";

    private final DatabaseOpenHelper _databaseHelper;

    public PunDatabase(Context context) {

        _databaseHelper = new DatabaseOpenHelper(context);
    }

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = KEYWORDS + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(_databaseHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

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
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        KEYWORDS + ", " +
                        PUNS + ")";

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            _context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            _database = db;
            _database.execSQL(FTS_TABLE_CREATE);
            buildDatabase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DATABASE", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }

        private void buildDatabase() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loadDatabase();
                }
            }).start();
        }

        private void loadDatabase() {
            HashSet<String> puns = new HashSet<>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(_context.getAssets().open(AnimalPuns.FILE_NAME)));
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    addPuns("animal",mLine);
                }
            } catch (IOException e) {

            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }
        }

        private void addPuns(String keyword, String pun) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEYWORDS, keyword);
            initialValues.put(PUNS, pun);

            _database.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }
    }
}