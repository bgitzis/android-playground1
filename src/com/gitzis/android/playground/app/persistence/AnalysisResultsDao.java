package com.gitzis.android.playground.app.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.Time;

import com.gitzis.android.playground.app.model.AnalyzeResult;
import com.gitzis.android.playground.app.persistence.fwk.DbQueryUtil;
import com.gitzis.android.playground.app.persistence.fwk.SQLiteConsts;

public class AnalysisResultsDao {
    public static final String TABLE_NAME = "analysis_results";
    public static final String DEFAULT_SORT_ORDER = "modified DESC";

    public static final class AnalysisResultsColumns implements BaseColumns {
        private AnalysisResultsColumns() {
        }

        public static final String CMN_ANALYZER_NAME = "analyzer_name";
        public static final String CMN_CREATE_DATE = "create_date";
        public static final String CMN_ANALYZE_RESULT = "analyze_result";
        public static final String CMN_UPLOAD_DATE = "upload_date";
    }

    SQLiteOpenHelper dbOpenHelper;

    public AnalysisResultsDao(SQLiteOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public void insert(AnalyzeResult result) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AnalysisResultsColumns.CMN_ANALYZER_NAME, result.getAnalyzerName());
        contentValues.put(AnalysisResultsColumns.CMN_ANALYZE_RESULT, result.getResult());
        contentValues.put(AnalysisResultsColumns.CMN_CREATE_DATE, result.getSampleDate().format2445());
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getRowsToUpload() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        return DbQueryUtil.query(db, TABLE_NAME, AnalysisResultsColumns.CMN_UPLOAD_DATE + " is null");
    }

    public void setUploaded(int rowid) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AnalysisResultsColumns.CMN_UPLOAD_DATE, now().format2445());
        db.update(TABLE_NAME, values, AnalysisResultsColumns._ID + " = ?", new String[] { Integer.toString(rowid) });
    }

    private Time now() {
        Time time = new Time();
        time.setToNow();
        return time;
    }

    static String getCreateTableSql() {
        return "CREATE TABLE " + TABLE_NAME + " (" //
                + AnalysisResultsColumns._ID + " INTEGER PRIMARY KEY,"//
                + AnalysisResultsColumns.CMN_ANALYZER_NAME + SQLiteConsts.TEXT + "," //
                + AnalysisResultsColumns.CMN_CREATE_DATE + SQLiteConsts.TEXT + ","//
                + AnalysisResultsColumns.CMN_UPLOAD_DATE + SQLiteConsts.TEXT + ","//
                + AnalysisResultsColumns.CMN_ANALYZE_RESULT + SQLiteConsts.TEXT//
                + ");";
    }

}
