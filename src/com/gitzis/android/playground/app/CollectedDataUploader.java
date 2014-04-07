package com.gitzis.android.playground.app;

import android.content.Context;
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
        HttpHelper.doPost("ääbc");
        Toast.makeText(context, "not ready yet", Toast.LENGTH_SHORT).show();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
