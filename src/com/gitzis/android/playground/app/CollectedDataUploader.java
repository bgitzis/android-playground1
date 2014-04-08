package com.gitzis.android.playground.app;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.gitzis.android.playground.app.net.HttpHelper;
import com.gitzis.android.playground.app.persistence.AnalysisResultsDao;
import com.gitzis.android.playground.app.persistence.AnalysisResultsDao.AnalysisResultsColumns;
import com.gitzis.android.playground.app.persistence.MyDbHelper;

public class CollectedDataUploader {
    Context context;
    private AnalysisResultsDao dao = new AnalysisResultsDao(MyDbHelper.getINSTANCE());

    public CollectedDataUploader(Context context) {
        this.context = context;
    }

    public void upload() {
        Cursor cursor = dao.getRowsToUpload();
        while (cursor.moveToNext()) {
            String analyzerName = cursor.getString(cursor.getColumnIndex(AnalysisResultsColumns.CMN_ANALYZER_NAME));
            String date = cursor.getString(cursor.getColumnIndex(AnalysisResultsColumns.CMN_CREATE_DATE));
            String result = cursor.getString(cursor.getColumnIndex(AnalysisResultsColumns.CMN_ANALYZE_RESULT));
            doUpload(analyzerName + ":" + date + ":" + result);
            dao.setUploaded(cursor.getInt(cursor.getColumnIndex(AnalysisResultsColumns._ID)));
        }
    }

    private void doUpload(String data) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                for (String param : params) {
                    Log.d(CollectedDataUploader.class.getSimpleName(), HttpHelper.doPost(param));
                }
                return null;
            }
        }.execute(data);
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
