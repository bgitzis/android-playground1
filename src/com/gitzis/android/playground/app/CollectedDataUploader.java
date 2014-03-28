package com.gitzis.android.playground.app;

import android.content.Context;
import android.widget.Toast;

public class CollectedDataUploader {
    Context context;

    public void upload() {
        Toast.makeText(context, "not ready yet", Toast.LENGTH_SHORT).show();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
