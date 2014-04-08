package com.gitzis.android.playground.app.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = MyDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "samples_data.db";
    private static final int DATABASE_VERSION = 1;

    private static MyDbHelper INSTANCE;

    private MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.w(LOG_TAG, "Delete " + DATABASE_NAME + "=" + context.deleteDatabase(DATABASE_NAME));
    }

    public static void init(Context context) {
        INSTANCE = new MyDbHelper(context);
    }

    public static MyDbHelper getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, SamplesDao.getCreateTableSql());
        createTable(db, AnalysisResultsDao.getCreateTableSql());
    }

    private void createTable(SQLiteDatabase db, String createTableSql) {
        Log.i(LOG_TAG, createTableSql);
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE" + SamplesDao.TABLE_NAME);
        db.execSQL("VACUUM");
    }
}
