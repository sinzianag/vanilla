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
