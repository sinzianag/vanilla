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

import android.database.Cursor;


class PunStorage {

    public PunStorage() {
    }

    public void initialize() {
    }

    /**
     * Get all the puns from the DataBase
     * @return Cursor with puns
     */
    public Cursor getPuns() {
        String COL_PUNS = "puns";
        String[] columns = {COL_PUNS};
        return null; //_db.query(PunDBHelper.PUN_TABLE, columns, null, null, null, null, null);
    }

    private Cursor searchForPuns(String query) {
        //String selection = PunDBHelper.PUN_TABLE + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return null; // _db.query(PunDBHelper.PUN_TABLE, null, selection, selectionArgs, null, null, null);
    }

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
        //String[] columns = {PunDBHelper.CAT_NAME};
        return null; // _db.query(PunDBHelper.CATEGORY_TABLE, columns, null, null, null, null, null);
    }
}
