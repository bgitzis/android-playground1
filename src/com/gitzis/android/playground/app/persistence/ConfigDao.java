package com.gitzis.android.playground.app.persistence;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ConfigDao {
    private static final String TABLE_NAME = "config";

    public static final class ConfigColumns implements BaseColumns {
        private ConfigColumns() {
        }

        public static final String CMN_NAME = "name";
        public static final String CMN_VALUE = "value";
    }

    SQLiteOpenHelper dbOpenHelper;

    public ConfigDao(SQLiteOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    static String getCreateTableSql() {
        return "CREATE TABLE " + TABLE_NAME + " (" //
                + ConfigColumns._ID + " INTEGER PRIMARY KEY,"//
                + ConfigColumns.CMN_NAME + " TEXT,"//
                + ConfigColumns.CMN_VALUE + " TEXT"//
                + ");";
    }
}
