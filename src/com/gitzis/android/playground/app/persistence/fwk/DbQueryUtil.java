package com.gitzis.android.playground.app.persistence.fwk;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbQueryUtil {
    /**
     * helper wrapper that executes query without clauses: bind variables, group by, having, order by
     * 
     * @param db
     * @param table name
     * @returns Cursor for specified table with all columns
     */
    public static Cursor query(SQLiteDatabase db, String table, String where) {
        return db.query(table, null, where, null, null, null, null);
    }
}
