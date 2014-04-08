package com.gitzis.android.playground.app;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gitzis.android.playground.app.net.HttpHelper;
import com.gitzis.android.playground.app.persistence.MyDbHelper;
import com.gitzis.android.playground.app.persistence.SamplesDao;

public class CollectedDataUploader {
    Context context;
    private SamplesDao samplesDao;

    public CollectedDataUploader(Context context) {
        this.context = context;
        this.samplesDao = new SamplesDao(MyDbHelper.getINSTANCE());
    }

    public void upload() {
        Cursor cursor = samplesDao.getRowsToUpload();
        if (!cursor.moveToFirst()) {
            Toast.makeText(context, "nothing to upload", Toast.LENGTH_SHORT).show();
            return;
        }
        doUpload("abcd");
        Toast.makeText(context, "not ready yet", Toast.LENGTH_SHORT).show();
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
