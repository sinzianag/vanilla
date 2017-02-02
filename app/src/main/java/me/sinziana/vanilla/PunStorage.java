package me.sinziana.vanilla;

import android.content.Context;
import android.database.Cursor;

/**
 * sinziana on 1/29/17.
 */

class PunStorage {

    private PunDBHelper _db;

    public PunStorage(Context context) {
        _db = new PunDBHelper(context);
    }

    /**
     * Get all the puns from the DataBase
     * @return Cursor with puns
     */
    public Cursor getPuns() {
        String COL_PUNS = "puns";
        String[] columns = {COL_PUNS};
        return _db.query(PunDBHelper.PUN_TABLE, columns, null, null, null, null, null);
    }

    /**
     * Search for puns
     * @param query - What we're searching for
     * @return Cursor with the puns returned
     */
    public Cursor searchForPuns(String query) {
        String selection = PunDBHelper.PUN_TABLE + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return _db.query(PunDBHelper.PUN_TABLE, null, selection, selectionArgs, null, null, null);
    }

    /**
     * Get the pun for today
     * @return String of today's pun
     */
    public String getTodaysPun() {
        String today = PunUtils.getTodayString();
        Cursor cur = this.searchForPuns(today);

        if (cur != null) {
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
               return cur.getString(0);
            }
        }

        return null;
    }

    public Cursor getCategories() {
        String[] columns = {PunDBHelper.CAT_NAME};
        return _db.query(PunDBHelper.CATEGORY_TABLE, columns, null, null, null, null, null);

//        if (cur != null) {
//            cur.moveToFirst();
//            while (cur.isAfterLast() == false) {
//                System.out.println("Categories: " +  cur.getString(0));
//                cur.moveToNext();
//            }
//        }
    }
}
