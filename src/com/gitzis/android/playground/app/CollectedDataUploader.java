package com.gitzis.android.playground.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gitzis.android.playground.app.net.HttpHelper;

public class CollectedDataUploader {
    Context context;

    public void upload() {
        //        Cursor cursor = SamplesRepository.getINSTANCE().getRowsToUpload();
        //        if (!cursor.moveToFirst()) {
        //            Toast.makeText(context, "nothing to upload", Toast.LENGTH_SHORT).show();
        //            return;
        //        }
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                for (String param : params) {
                    Log.d(CollectedDataUploader.class.getSimpleName(), HttpHelper.doPost(param));
                }
                return null;
            }
        }.execute("abcd");
        Toast.makeText(context, "not ready yet", Toast.LENGTH_SHORT).show();
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
