package me.sinziana.vanilla;

import android.content.Context;
import android.database.Cursor;

/**
 * sinziana on 1/29/17.
 */

public class PunStorage {

    private PunDBHelper _db;

    private final String COL_PUNS = "puns";

    public PunStorage(Context context) {
        _db = new PunDBHelper(context);
    }

//    public Cursor getWordMatches(String query, String[] columns) {
//        String selection = FTS_VIRTUAL_TABLE + " MATCH ?";
//        String[] selectionArgs = new String[] {query+"*"};
//
//        return query(selection, selectionArgs, columns);
//    }
//
//    public Cursor getRandomPun() {
//        return null;
//    }
//
//    public Cursor getPunForToday() {
//        return null;
//    }
//
//    /**
//     * Get the puns assigned for a certain pun category
//     * @param category - one of the PunCategories
//     * @return Cursor with all the puns
//     */
//    public Cursor getPunsForCategory(String category) {
//        return null;
//    }
//
//    public Cursor getFavoritePuns() {
//        return null;
//    }

    public Cursor getPuns() {
        String[] columns = {COL_PUNS};
        return _db.query(_db.PUN_TABLE, columns, null, null, null, null, null);
    }

    public Cursor searchForPuns(String query) {
        String selection = _db.PUN_TABLE + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return _db.query(_db.PUN_TABLE, null, selection, selectionArgs, null, null, null);
    }

    public String getTodaysPun() {
        String today = PunUtils.getTodayString();
        Cursor cur = this.searchForPuns(today);

        if (cur != null) {
            cur.moveToFirst();
            while (cur.isAfterLast() == false) {
               return cur.getString(0);
            }
        }

        return null;
    }

    public Cursor getCategories() {
        String[] columns = {_db.CAT_NAME};
        return _db.query(_db.CATEGORY_TABLE, columns, null, null, null, null, null);

//        if (cur != null) {
//            cur.moveToFirst();
//            while (cur.isAfterLast() == false) {
//                System.out.println("Categories: " +  cur.getString(0));
//                cur.moveToNext();
//            }
//        }
    }
}
