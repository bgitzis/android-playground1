package com.gitzis.android.playground.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.model.SensorResult;

public class SamplesRepository extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "samples_data.db";
    private static final int DATABASE_VERSION = 1;

    private static SamplesRepository INSTANCE;

    public static void init(Context context) {
        INSTANCE = new SamplesRepository(context);
    }

    public static SamplesRepository getINSTANCE() {
        return INSTANCE;
    }

    private SamplesRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SamplesTable.TABLE_NAME + " (" //
                + SamplesTable._ID + " INTEGER PRIMARY KEY,"//
                + SamplesTable.CMN_SENSOR + " TEXT,"//
                + SamplesTable.CMN_SAMPLE_CREATE_DATE + " TEXT,"//
                + SamplesTable.CMN_UPLOAD_DATE + " INTEGER,"//
                + SamplesTable.CMN_X + " INTEGER"//
                + SamplesTable.CMN_Y + " INTEGER"//
                + SamplesTable.CMN_Z + " INTEGER"//
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("unimplmented method for " + SamplesTable.TABLE_NAME);
    }

    public void insert(String sensorName, Time sampleCreationTime, Sample sample) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (SensorResult sensorResult : sample.getSensorResults()) {
            float[] sensorValues = sensorResult.getValues();
            contentValues.put(SamplesTable.CMN_X, sensorValues[0]);
            if (sensorValues.length > 1) {
                contentValues.put(SamplesTable.CMN_Y, sensorValues[1]);
            }
            if (sensorValues.length > 2) {
                contentValues.put(SamplesTable.CMN_Z, sensorValues[2]);
            }
            contentValues.put(SamplesTable.CMN_OFFSET_MILLIS, sensorResult.getTimeNanos());
            contentValues.put(SamplesTable.CMN_SENSOR, sensorName);
            contentValues.put(SamplesTable.CMN_SAMPLE_CREATE_DATE, sample.getSampleDate().format2445());
            db.insert(SamplesTable.TABLE_NAME, null, contentValues);
        }
    }

    public Cursor getRowsToUpload() {
        return query(SamplesTable.TABLE_NAME, SamplesTable.CMN_UPLOAD_DATE + " is null");
    }

    /**
     * helper wrapper that executes query without clauses: bind variables, group by, having, order by
     * 
     * @param table name
     * @returns Cursor for specified table with all columns
     */
    private Cursor query(String table, String where) {
        return getReadableDatabase().query(table, null, where, null, null, null, null);
    }
}
