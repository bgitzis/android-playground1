package com.gitzis.android.playground.app.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.Time;

import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.model.SensorResult;
import com.gitzis.android.playground.app.persistence.fwk.DbQueryUtil;

public class SamplesDao {
    public static final String TABLE_NAME = "samples";
    public static final String DEFAULT_SORT_ORDER = "modified DESC";

    public static final class SamplesColumns implements BaseColumns {
        private SamplesColumns() {
        }

        public static final String CMN_SENSOR = "sensor_type";
        public static final String CMN_SAMPLE_CREATE_DATE = "create_date";
        public static final String CMN_UPLOAD_DATE = "upload_date";
        public static final String CMN_X = "x";
        public static final String CMN_Y = "y";
        public static final String CMN_Z = "z";
        public static final String CMN_OFFSET_MILLIS = "offset_millis";
    }

    SQLiteOpenHelper dbOpenHelper;

    public SamplesDao(SQLiteOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public void insert(String sensorName, Time sampleCreationTime, Sample sample) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (SensorResult sensorResult : sample.getSensorResults()) {
            float[] sensorValues = sensorResult.getValues();
            contentValues.put(SamplesColumns.CMN_X, sensorValues[0]);
            if (sensorValues.length > 1) {
                contentValues.put(SamplesColumns.CMN_Y, sensorValues[1]);
            }
            if (sensorValues.length > 2) {
                contentValues.put(SamplesColumns.CMN_Z, sensorValues[2]);
            }
            contentValues.put(SamplesColumns.CMN_OFFSET_MILLIS, sensorResult.getTimeNanos());
            contentValues.put(SamplesColumns.CMN_SENSOR, sensorName);
            contentValues.put(SamplesColumns.CMN_SAMPLE_CREATE_DATE, sample.getSampleDate().format2445());
            db.insert(TABLE_NAME, null, contentValues);
        }
    }

    public Cursor getRowsToUpload() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        return DbQueryUtil.query(db, TABLE_NAME, SamplesColumns.CMN_UPLOAD_DATE + " is null");
    }

    static String getCreateTableSql() {
        return "CREATE TABLE " + TABLE_NAME + " (" //
                + SamplesColumns._ID + " INTEGER PRIMARY KEY,"//
                + SamplesColumns.CMN_SENSOR + " TEXT,"//
                + SamplesColumns.CMN_SAMPLE_CREATE_DATE + " TEXT,"//
                + SamplesColumns.CMN_UPLOAD_DATE + " INTEGER,"//
                + SamplesColumns.CMN_X + " INTEGER"//
                + SamplesColumns.CMN_Y + " INTEGER"//
                + SamplesColumns.CMN_Z + " INTEGER"//
                + ");";
    }
}
