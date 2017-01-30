package me.sinziana.vanilla;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        String selection = FTS_VIRTUAL_TABLE + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    public Cursor getRandomPun() {
        return null;
    }

    public Cursor getPunForToday() {
        return null;
    }

    /**
     * Get the puns assigned for a certain pun category
     * @param category - one of the PunCategories
     * @return Cursor with all the puns
     */
    public Cursor getPunsForCategory(String category) {
        return null;
    }

    public Cursor getFavoritePuns() {
        return null;
    }

    /*
     *  TODO: Update Puns
     */

    /**
     * Access the database
     * @param selection
     * @param selectionArgs
     * @param columns
     * @return
     */
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
                    loadDatabase(AnimalPuns.FILE_NAME, PunCategory.ANIMAL);
                    loadDatabase(BatteryPuns.FILE_NAME, PunCategory.BATTERY);
                    loadDatabase(ComputerPuns.FILE_NAME, PunCategory.COMPUTER);
                    loadDatabase(ElevatorPuns.FILE_NAME, PunCategory.ELEVATOR);
                    loadDatabase(FoodPuns.FILE_NAME, PunCategory.FOOD);
                    loadDatabase(SpacePuns.FILE_NAME, PunCategory.SPACE);
                }
            }).start();
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
