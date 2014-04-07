package com.gitzis.android.playground.app.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "samples_data.db";
    private static final int DATABASE_VERSION = 1;

    private static MyDbHelper INSTANCE;

    public static void init(Context context) {
        INSTANCE = new MyDbHelper(context);
    }

    public static MyDbHelper getINSTANCE() {
        return INSTANCE;
    }

    private MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SamplesDao.getCreateTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("unimplmented method for " + SamplesDao.TABLE_NAME);
    }

}
